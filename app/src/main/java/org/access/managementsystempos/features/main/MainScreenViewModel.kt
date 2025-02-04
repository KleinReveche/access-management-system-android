package org.access.managementsystempos.features.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.access.managementsystempos.data.PreferenceKey
import org.access.managementsystempos.data.PreferenceKeys
import org.access.managementsystempos.data.writeDataStore

class MainScreenViewModel : ViewModel() {
    fun onLogout(context: Context, onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            context.writeDataStore(PreferenceKeys[PreferenceKey.LOGIN_TOKEN]!!, "")
            context.writeDataStore(PreferenceKeys[PreferenceKey.CASHIER_NAME]!!, "")
            context.writeDataStore(PreferenceKeys[PreferenceKey.LOGIN_TIME]!!, "")
            onLogoutComplete()
        }
    }
}