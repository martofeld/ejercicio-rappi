package com.mfeldsztejn.rappitest.repositories.configurations

import androidx.lifecycle.LiveData
import com.mfeldsztejn.rappitest.dtos.Configuration

interface ConfigurationRepository {
    fun loadConfiguration(): LiveData<Configuration>

    fun hasConfiguration(): LiveData<Boolean>

    fun saveConfiguration(configuration: Configuration)
}