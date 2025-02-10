package org.access.managementsystempos.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.R
import org.access.managementsystempos.features.kitchen.KitchenDestination
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.POSScreenDestination
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val vm: MainScreenViewModel = koinViewModel()

    val screens = listOf(
        Triple("Profile", R.drawable.ic_profile, 0),
        Triple("Home", R.drawable.ic_home, 1),
        Triple("Settings", R.drawable.ic_settings, 2)
    )

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Access™", color = MaterialTheme.colorScheme.onPrimary) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    screens.forEach { (label, iconRes, index) ->
                        NavigationBarItem(
                            selected = vm.selectedItem == index,
                            onClick = { vm.selectedItem = index },
                            icon = {
                                Image(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = label
                                )
                            },
                            label = { Text(label, color = MaterialTheme.colorScheme.onPrimary) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                when (vm.selectedItem) {
                    0 -> ProfileScreen(
                        navController,
                        vm,
                        vm.loginToken,
                        vm.cashierName,
                        vm.loginTime.toString()
                    )

                    1 -> HomeScreen(navController)
                    2 -> SettingsScreen()
                }
            }
        }
}

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: MainScreenViewModel,
    loginToken: String,
    cashierName: String,
    loginTime: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Login Token: $loginToken", color = MaterialTheme.colorScheme.primary)
        Text("Cashier Name: $cashierName", color = MaterialTheme.colorScheme.primary)
        Text("Login Time: $loginTime", color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(16.dp))

        ElevatedButton(
            onClick = {
                vm.onLogout {
                    navController.navigate(LoginScreenDestination)
                }
            },
            colors = ButtonDefaults.elevatedButtonColors(containerColor = MaterialTheme.colorScheme.onPrimary)  // ✅ Apply Theme Color
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    val screens = listOf(
        Triple("POS", POSScreenDestination, R.drawable.ic_pos),
        Triple("Kitchen", KitchenDestination, R.drawable.ic_kitchen)
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(screens.size) { index ->
            val (text, destination, iconRes) = screens[index]
            ElevatedCardButton(text, iconRes) {
                navController.navigate(destination)
            }
        }
    }
}

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings Screen (Coming Soon)", color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ElevatedCardButton(text: String, iconRes: Int, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp)
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large, // ✅ Apply Theme Shape
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)  // ✅ Apply Theme Color
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, color = MaterialTheme.colorScheme.onPrimary)  // ✅ Apply Theme Color
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
