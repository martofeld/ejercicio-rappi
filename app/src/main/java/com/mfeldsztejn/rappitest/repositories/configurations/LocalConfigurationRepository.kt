package com.mfeldsztejn.rappitest.repositories.configurations

import android.os.AsyncTask
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mfeldsztejn.rappitest.AppDatabase
import com.mfeldsztejn.rappitest.Constants
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.utils.DateUtils

class LocalConfigurationRepository : ConfigurationRepository {

    override fun loadConfiguration(): LiveData<Configuration> {
        return AppDatabase.getInstance()
                .configurationDao()
                .find(Constants.CONFIGURATION_ID)
    }

    override fun hasConfiguration(): LiveData<Boolean> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.application)
        val lastUpdate = preferences.getLong(Constants.CONFIGURATION_LAST_UPDATE, 0)
        if (DateUtils.isOneMonthOld(lastUpdate)) {
            // If its a month old update it
            val liveData = MutableLiveData<Boolean>()
            liveData.postValue(false)
            return liveData
        }

        return Transformations.map(
                AppDatabase.getInstance()
                        .configurationDao()
                        .count()
        ) {
            it != 0
        }
    }

    override fun saveConfiguration(configuration: Configuration) {
        AsyncTask.execute(SaveConfigurationRunnable(configuration))
    }

}