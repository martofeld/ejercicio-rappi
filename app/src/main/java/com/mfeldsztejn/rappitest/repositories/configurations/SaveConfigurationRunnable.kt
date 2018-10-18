package com.mfeldsztejn.rappitest.repositories.configurations

import android.preference.PreferenceManager
import com.mfeldsztejn.rappitest.AppDatabase
import com.mfeldsztejn.rappitest.Constants
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.dtos.Configuration
import java.util.*

class SaveConfigurationRunnable(val configuration: Configuration) : Runnable {
    override fun run() {
        configuration.id = Constants.CONFIGURATION_ID
        AppDatabase
                .getInstance()
                .configurationDao()
                .insert(configuration)

        val preferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.application)
        preferences
                .edit()
                .putLong(Constants.CONFIGURATION_LAST_UPDATE, Date().time)
                .apply()
    }
}