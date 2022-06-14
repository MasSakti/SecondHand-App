package id.co.binar.secondhand.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

class DataStoreManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore by preferencesDataStore(DATASTORE_SETTINGS)
        private val USR_ID = stringPreferencesKey("USER_TOKEN")
    }

    fun setUserId(value: String) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.setValue(USR_ID, value)
        }
    }

    fun getUserId() = runBlocking { context.dataStore.getValue(USR_ID, "").firstOrNull() }
}

fun <T> DataStore<Preferences>.getValue(
    key: Preferences.Key<T>,
    defaultValue: T
) = this.data
    .catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key] ?: defaultValue
    }

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

suspend fun <T> DataStore<Preferences>.removeValue(key: Preferences.Key<T>) {
    this.edit { preferences ->
        preferences.remove(key)
    }
}

suspend fun DataStore<Preferences>.clear() {
    this.edit { preferences ->
        preferences.clear()
    }
}