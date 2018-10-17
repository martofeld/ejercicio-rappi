package com.mfeldsztejn.rappitest.repositories

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.events.ConfigurationObtainedEvent
import com.mfeldsztejn.rappitest.networking.ApiError
import com.mfeldsztejn.rappitest.networking.DefaultCallback
import com.mfeldsztejn.rappitest.networking.NetworkingBus
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

interface ConfigurationRepository {
    fun loadConfiguration()

    fun hasConfiguration(): Boolean

    fun saveConfiguration(configuration: Configuration)
}

class NetworkConfigurationRepository : ConfigurationRepository {
    // Assume we always have it
    override fun hasConfiguration(): Boolean = true

    override fun saveConfiguration(configuration: Configuration) {
        // This might eventually be a post...
    }

    override fun loadConfiguration() {
        NetworkingBus.register(this)
        ConfigurationApi
                .build()
                .configuration()
                .enqueue(DefaultCallback<Configuration>())
    }

    @Subscribe
    fun onConfigurationSuccess(configuration: Configuration) {
        NetworkingBus.unregister(this)
        MainApplication.configuration = configuration
        EventBus.getDefault().post(ConfigurationObtainedEvent(configuration))
    }

    @Subscribe
    fun onConfigurationFailed(apiError: ApiError) {
        // TODO
    }

}

const val CONFIGURATION_KEY = "configuration"

class LocalConfigurationRepository(context: Context) : ConfigurationRepository {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    override fun loadConfiguration() {
        val configString = preferences.getString(CONFIGURATION_KEY, null)
        if (configString != null) {
            val config = Gson().fromJson(configString, Configuration::class.java)
            EventBus.getDefault().post(ConfigurationObtainedEvent(config))
        }
    }

    override fun hasConfiguration(): Boolean {
        return preferences.contains(CONFIGURATION_KEY)
    }

    override fun saveConfiguration(configuration: Configuration) {
        preferences
                .edit()
                .putString(CONFIGURATION_KEY, Gson().toJson(configuration))
                .apply()
    }

}