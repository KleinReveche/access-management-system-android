package org.access.managementsystempos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.access.managementsystempos.domain.models.PreferenceKey
import org.access.managementsystempos.domain.models.ResponseType
import org.access.managementsystempos.domain.repository.LocalRepository
import org.access.managementsystempos.domain.repository.RemoteRepository
import org.access.managementsystempos.features.common.getProducts
import org.access.managementsystempos.features.login.LoginScreenDestination
import org.access.managementsystempos.features.main.MainScreenDestination
import org.access.managementsystempos.features.navigation.ScreenDestination

class MainActivityViewModel(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    var startDestination: ScreenDestination by mutableStateOf(LoginScreenDestination)

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val loginToken = localRepository.getPreference(PreferenceKey.LOGIN_TOKEN)?.value

            if (loginToken != null) {
                val role = remoteRepository.getRole()
                if (role.responseType == ResponseType.SUCCESS) {
                    startDestination = MainScreenDestination
                    getProducts(remoteRepository, localRepository)
                }
            }

            _isLoading.value = false
        }
    }
}