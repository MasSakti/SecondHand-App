package binar.and3.kelompok1.secondhand

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {

    object PrefDatastore {
        const val PREF_NAME = "SecondHand"
        val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
    }

    object Named {
        const val BASE_URL_SECONDHAND = "BASE_URL_SECONDHAND"

        const val RETROFIT_SECONDHAND = "RETROFIT_SECONDHAND"
    }
}