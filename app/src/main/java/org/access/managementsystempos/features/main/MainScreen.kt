package org.access.managementsystempos.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.R
import org.access.managementsystempos.features.main.components.HomeScreen
import org.access.managementsystempos.features.main.components.ProfileScreen
import org.access.managementsystempos.features.main.components.SettingsScreen
import org.access.managementsystempos.features.navigation.ScreenDestination
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
                    val isSelected = vm.selectedItem == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { vm.selectedItem = index },
                        icon = {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = label,
                                colorFilter = ColorFilter.tint(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        },
                        label = {
                            Text(
                                label,
                                color = if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
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


@Serializable
object MainScreenDestination : ScreenDestination

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(NavController(LocalContext.current))
}
