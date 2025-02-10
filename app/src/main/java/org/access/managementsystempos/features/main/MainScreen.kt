package org.access.managementsystempos.features.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.access.managementsystempos.data.PreferenceKey
import org.access.managementsystempos.data.PreferenceKeys
import org.access.managementsystempos.data.readDataStore
import org.access.managementsystempos.features.kitchen.KitchenDestination
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.POSScreenDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val vm: MainScreenViewModel = viewModel { MainScreenViewModel() }

    val context = LocalContext.current
    var loginToken by remember { mutableStateOf("") }
    var cashierName by remember { mutableStateOf("") }
    var loginTime by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loginToken = context.readDataStore(PreferenceKeys[PreferenceKey.LOGIN_TOKEN]!!)!!
        cashierName = context.readDataStore(PreferenceKeys[PreferenceKey.CASHIER_NAME]!!)!!
        loginTime = context.readDataStore(PreferenceKeys[PreferenceKey.LOGIN_TIME]!!)!!
    }

    val listState = rememberLazyListState()
    val screens = listOf(
        "Go to POS Screen" to POSScreenDestination,
        "Go to Kitchen Screen" to KitchenDestination
    )
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .background(Color.Gray)
            ) {
                DrawerContent(navController, vm, context) {
                    coroutineScope.launch { drawerState.close() }
                }
            }
        },
        gesturesEnabled = true,
        scrimColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Access™") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("Hello World!")
                Text("Login Token: $loginToken")
                Text("Cashier Name: $cashierName")
                Text("Login Time: $loginTime")

                Spacer(modifier = Modifier.height(16.dp))

                LazyRow(
                    state = listState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(Int.MAX_VALUE) { index ->
                        val (text, destination) = screens[index % screens.size]
                        ElevatedCardButton(text) {
                            navController.navigate(destination)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    vm: MainScreenViewModel,
    context: Context,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .padding(16.dp)
    ) {
        Text("Profile", modifier = Modifier
            .padding(8.dp)
            .clickable { onClose() })
        Text("Settings", modifier = Modifier
            .padding(8.dp)
            .clickable { onClose() })
        Text("Logout", modifier = Modifier
            .padding(8.dp)
            .clickable {
                vm.onLogout(context) { navController.navigate(org.access.managementsystempos.features.login.LoginScreenDestination) }
                onClose()
            })
    }
}

@Composable
fun ElevatedCardButton(text: String, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text)
        }
    }
}

@Serializable
object MainScreenDestination : ScreenDestination

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(NavController(LocalContext.current))
}

