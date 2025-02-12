package org.access.managementsystempos.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.features.main.components.FeatureButtonGrid
import org.access.managementsystempos.features.main.components.LogoutButton
import org.access.managementsystempos.features.main.components.UserProfileSection
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navController: NavController) {
    val vm: MainScreenViewModel = koinViewModel()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserProfileSection(vm)
            Spacer(modifier = Modifier.height(24.dp))
            LogoutButton(vm, navController)
            Spacer(modifier = Modifier.height(24.dp))
            FeatureButtonGrid(vm, navController)
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
