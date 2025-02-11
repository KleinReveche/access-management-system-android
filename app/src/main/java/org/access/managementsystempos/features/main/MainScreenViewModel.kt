package org.access.managementsystempos.features.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.access.managementsystempos.domain.models.PreferenceKey
import org.access.managementsystempos.domain.models.ResponseType
import org.access.managementsystempos.domain.models.db.Preference
import org.access.managementsystempos.domain.repository.LocalRepository
import org.access.managementsystempos.domain.repository.RemoteRepository


class MainScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {
    var error by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var loginToken by mutableStateOf("")
    var cashierName by mutableStateOf("")
    var loginTime by mutableStateOf(Clock.System.now())
    var role by mutableStateOf("")
    var selectedItem by mutableIntStateOf(1)

    init {
        viewModelScope.launch {
            loginToken = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)!!.value
            cashierName = localRepository.getPreference(PreferenceKey.CASHIER_NAME)!!.value
            loginTime =
                Instant.parse(localRepository.getPreference(PreferenceKey.LOGIN_TIME)!!.value)
            role = remoteRepository.getRole().message
        }
    }

    fun onLogout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            println("Attempting to log out")

            val token = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)?.value.orEmpty()
            println("Login token: $token")

            if (token.isNotEmpty()) {
                val logout = remoteRepository.logout(token)
                println("Logout response: ${logout.responseType}")

                if (logout.responseType == ResponseType.SUCCESS) {
                    println("Logout successful, clearing preferences")

                    localRepository.savePreference(Preference(PreferenceKey.LOGIN_TOKEN.name, ""))
                    localRepository.savePreference(Preference(PreferenceKey.CASHIER_NAME.name, ""))
                    localRepository.savePreference(Preference(PreferenceKey.LOGIN_TIME.name, ""))
                    localRepository.savePreference(Preference(PreferenceKey.ROLE.name, ""))

                    loginToken = ""
                    cashierName = ""
                    loginTime = Clock.System.now()
                    role = ""

                    println("Navigating to login screen")
                    onLogoutComplete()
                } else {
                    println("Logout failed: ${logout.message}")
                    error = true
                    errorMessage = logout.message
                    onLogoutComplete()
                }
            } else {
                println("No token found, forcing navigation to login...")
                onLogoutComplete()
            }
        }
    }
}