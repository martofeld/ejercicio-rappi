package com.mfeldsztejn.rappitest

import android.content.Context
import com.mfeldsztejn.rappitest.repositories.LocalConfigurationRepository
import com.mfeldsztejn.rappitest.repositories.NetworkConfigurationRepository

class ConfigurationManager(context: Context) {
    private val localRepository = LocalConfigurationRepository(context)
    private val networkRepository = NetworkConfigurationRepository()

    fun loadConfiguration() {
        if (localRepository.hasConfiguration()) {
            localRepository.loadConfiguration()
        } else {
            networkRepository.loadConfiguration()
        }
    }
}