package org.access.managementsystem.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.access.managementsystem.domain.models.Preference
import org.access.managementsystem.domain.models.PreferenceKey
import org.access.managementsystem.domain.models.ResponseType
import org.access.managementsystem.domain.repository.LocalRepository
import org.access.managementsystem.domain.repository.RemoteRepository
import org.access.managementsystem.features.common.getProducts

class LoginScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)

    var loggingIn by mutableStateOf(false)
    var loginError by mutableStateOf(false)
    var loginErrorMessage by mutableStateOf("")

    fun login(onLoginSuccess: () -> Unit, onLoginFailure: () -> Unit) {
        if (username.isEmpty() || password.isEmpty()) return
        loginErrorMessage = ""

        viewModelScope.launch {
            val login = remoteRepository.login(username, password)

            when (login.responseType) {
                ResponseType.SUCCESS -> {
                    localRepository.savePreference(
                        Preference(
                            PreferenceKey.LOGIN_TOKEN.name,
                            login.message
                        )
                    )
                    localRepository.savePreference(
                        Preference(
                            PreferenceKey.LOGIN_TIME.name,
                            Clock.System.now().toString()
                        )
                    )
                    localRepository.savePreference(
                        Preference(
                            PreferenceKey.CASHIER_NAME.name,
                            username
                        )
                    )
                    getProducts(remoteRepository, localRepository)
                    onLoginSuccess()
                }

                ResponseType.ERROR -> {
                    loginErrorMessage = login.message
                    loginError = true
                    onLoginFailure()
                }
            }
        }
    }
}