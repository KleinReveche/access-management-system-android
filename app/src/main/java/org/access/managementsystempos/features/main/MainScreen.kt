package org.access.managementsystempos.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.pos.POSScreenDestination

@Composable
fun MainScreen(navController: NavController) {
    Column {
        Text("Hello World!")
        Button(onClick = {
            navController.navigate(LoginScreenDestination)
        }) {
            Text("Go to Login Screen")
        }
    }
}

@Serializable
object MainScreenDestination
object MainScreenDestination : ScreenDestination
