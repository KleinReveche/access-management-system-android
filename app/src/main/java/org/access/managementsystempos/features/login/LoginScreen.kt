package org.access.managementsystempos.features.login

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import org.access.managementsystempos.features.navigation.ScreenDestination

@Composable
fun LoginScreen() {
    Text("Login Screen")
}

@Serializable
object LoginScreenDestination
object LoginScreenDestination : ScreenDestination