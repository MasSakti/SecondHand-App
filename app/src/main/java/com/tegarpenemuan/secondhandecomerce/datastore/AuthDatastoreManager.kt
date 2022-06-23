package com.tegarpenemuan.secondhandecomerce.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.tegarpenemuan.secondhandecomerce.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * com.tegarpenemuan.secondhandecomerce.datastore
 *
 * Created by Tegar Penemuan on 24/06/2022.
 * https://github.com/tegarpenemuanr3
 *
 */

class AuthDatastoreManager @Inject constructor(private val context: Context) {
    companion object {
        val Context.dataStoreAuth: DataStore<Preferences> by preferencesDataStore(
            name = Constant.PrefDataSore.PREF_NAME,
            produceMigrations = Companion::sharedPreferencesMigration
        )

        private fun sharedPreferencesMigration(context: Context) =
            listOf(SharedPreferencesMigration(context, Constant.PrefDataSore.PREF_NAME))
    }

    fun getToken(): Flow<String> {
        return context.dataStoreAuth.data.map { preferences ->
            preferences[Constant.PrefDataSore.TOKEN].orEmpty()
        }
    }

    suspend fun setToken(value: String) {
        context.dataStoreAuth.edit { preferences ->
            preferences[Constant.PrefDataSore.TOKEN] = value
        }
    }

    fun getId(): Flow<String> {
        return context.dataStoreAuth.data.map { preferences ->
            preferences[Constant.PrefDataSore.ID].orEmpty()
        }
    }

    suspend fun setID(value: String) {
        context.dataStoreAuth.edit { preferences ->
            preferences[Constant.PrefDataSore.ID] = value
        }
    }


    fun getEmail(): Flow<String> {
        return context.dataStoreAuth.data.map { preferences ->
            preferences[Constant.PrefDataSore.EMAIL].orEmpty()
        }
    }

    suspend fun setEmail(value: String) {
        context.dataStoreAuth.edit { preferences ->
            preferences[Constant.PrefDataSore.EMAIL] = value
        }
    }

    fun getName(): Flow<String> {
        return context.dataStoreAuth.data.map { preferences ->
            preferences[Constant.PrefDataSore.NAME].orEmpty()
        }
    }

    suspend fun setName(value: String) {
        context.dataStoreAuth.edit { preferences ->
            preferences[Constant.PrefDataSore.NAME] = value
        }
    }
}