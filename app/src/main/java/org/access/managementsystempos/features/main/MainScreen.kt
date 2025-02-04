package org.access.managementsystempos.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.data.PreferenceKey
import org.access.managementsystempos.data.PreferenceKeys
import org.access.managementsystempos.data.readDataStore
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.access.managementsystempos.features.pos.POSScreenDestination

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

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Hello World!")
            Text("Login Token: $loginToken")
            Text("Cashier Name: $cashierName")
            Text("Login Time: $loginTime")
            Button(onClick = {
                navController.navigate(POSScreenDestination)
            }) {
                Text("Go to POS Screen")
            }
            Button(onClick = { vm.onLogout(context) { navController.navigate(LoginScreenDestination) } }) {
                Text("Logout")
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
