package org.access.managementsystempos.features.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.access.managementsystempos.data.PreferenceKey
import org.access.managementsystempos.data.PreferenceKeys
import org.access.managementsystempos.data.dataStore
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class LoginScreenViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)

    @OptIn(ExperimentalUuidApi::class)
    fun login(context: Context, onLoginSuccess: () -> Unit) {
        if (username.isEmpty() || password.isEmpty()) return

        // TODO: Implement login logic, temporary for now

        if (username == "admin" && password == "admin") {
            val loginToken = Uuid.random().toString()
            val loginTime = Clock.System.now().toString()

            viewModelScope.launch {
                context.dataStore.edit { preferences ->
                    preferences[PreferenceKeys[PreferenceKey.LOGIN_TOKEN]!!] = loginToken
                    preferences[PreferenceKeys[PreferenceKey.LOGIN_TIME]!!] = loginTime
                    preferences[PreferenceKeys[PreferenceKey.CASHIER_NAME]!!] = username
                }

                onLoginSuccess()
            }
        }
    }
}