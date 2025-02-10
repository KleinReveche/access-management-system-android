package org.access.managementsystempos.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.access.managementsystempos.features.common.components.ErrorDialog
import org.access.managementsystempos.features.common.icons.Visibility
import org.access.managementsystempos.features.common.icons.VisibilityOff
import org.access.managementsystempos.features.main.MainScreenDestination
import org.access.managementsystempos.features.navigation.ScreenDestination
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val vm: LoginScreenViewModel = koinViewModel()

    Scaffold { paddingValues ->
        if (vm.loginError) {
            ErrorDialog(
                onDismissRequest = { vm.loginError = false },
                onConfirmation = { vm.loginError = false },
                vm.loginErrorMessage
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = vm.username,
                onValueChange = { vm.username = it },
                label = { Text("Username") },
                enabled = !vm.loggingIn,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = vm.password,
                onValueChange = { vm.password = it },
                label = { Text("Password") },
                enabled = !vm.loggingIn,
                visualTransformation = if (vm.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (vm.passwordVisible) VisibilityOff else Visibility
                    IconButton(onClick = { vm.passwordVisible = !vm.passwordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    vm.loggingIn = true
                    vm.login(
                        onLoginSuccess = {
                            vm.loggingIn = false
                            navController.navigate(MainScreenDestination)
                        },
                        onLoginFailure = {
                            vm.loggingIn = false
                            vm.loginError = true
                        }
                    )
                },
                enabled = !vm.loggingIn,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}

@Serializable
object LoginScreenDestination : ScreenDestination

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}