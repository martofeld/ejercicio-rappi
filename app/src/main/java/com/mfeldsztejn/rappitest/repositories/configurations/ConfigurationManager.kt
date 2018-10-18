package com.mfeldsztejn.rappitest.repositories.configurations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mfeldsztejn.rappitest.dtos.Configuration

class ConfigurationManager(val context: Context) {

    private val localRepository = LocalConfigurationRepository()
    private val networkRepository = NetworkConfigurationRepository()

    fun loadConfiguration(): LiveData<Configuration> {
        return Transformations.switchMap(localRepository.hasConfiguration()) {
            if (it) {
                localRepository.loadConfiguration()
            } else {
                networkRepository.loadConfiguration()
            }
        }
    }
}