package org.access.managementsystempos.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "access_management_system_pos_preferences")

suspend fun Context.readDataStore(key: Preferences.Key<String>): String? {
    return dataStore.data.first()[key]
}

suspend fun Context.writeDataStore(key: Preferences.Key<String>, value: String) {
    dataStore.edit { preferences ->
        preferences[key] = value
    }
}

suspend fun Context.setupDataStore() {
    writeDataStore(PreferenceKeys[PreferenceKey.LOGIN_TOKEN]!!, "")
    writeDataStore(PreferenceKeys[PreferenceKey.CASHIER_NAME]!!, "")
    writeDataStore(PreferenceKeys[PreferenceKey.LOGIN_TIME]!!, "")
}

val PreferenceKeys = mapOf(
    PreferenceKey.LOGIN_TOKEN to stringPreferencesKey(PreferenceKey.LOGIN_TOKEN.key),
    PreferenceKey.CASHIER_NAME to stringPreferencesKey(PreferenceKey.CASHIER_NAME.key),
    PreferenceKey.LOGIN_TIME to stringPreferencesKey(PreferenceKey.LOGIN_TIME.key),
)

enum class PreferenceKey(val key: String) {
    LOGIN_TOKEN("LOGIN_TOKEN"),
    CASHIER_NAME("CASHIER_NAME"),
    LOGIN_TIME("LOGIN_TIME"),
}