package com.tegarpenemuan.secondhandecomerce

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {

    object PrefDataSore {
        const val PREF_NAME = "MyDatastore"
        val ID = stringPreferencesKey("ID")
        val NAME = stringPreferencesKey("NAME")
        val EMAIL = stringPreferencesKey("EMAIL")
        val TOKEN = stringPreferencesKey("TOKEN")
    }
    
}