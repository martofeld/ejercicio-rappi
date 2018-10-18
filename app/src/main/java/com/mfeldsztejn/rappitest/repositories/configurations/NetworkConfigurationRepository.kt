package com.mfeldsztejn.rappitest.repositories.configurations

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.networking.ApiError
import com.mfeldsztejn.rappitest.networking.DefaultCallback
import com.mfeldsztejn.rappitest.networking.NetworkingBus
import org.greenrobot.eventbus.Subscribe

class NetworkConfigurationRepository : ConfigurationRepository {
    private val configLiveData = MutableLiveData<Configuration>()

    // Assume we always have it
    override fun hasConfiguration(): LiveData<Boolean> {
        val booleanLiveData = MutableLiveData<Boolean>()
        booleanLiveData.postValue(true)
        return booleanLiveData
    }

    override fun saveConfiguration(configuration: Configuration) {
        // This might eventually be a post...
    }

    override fun loadConfiguration(): LiveData<Configuration> {
        NetworkingBus.register(this)
        ConfigurationApi.build()
                .obtainConfiguration()
                .enqueue(DefaultCallback<Configuration>())
        return configLiveData
    }

    @Subscribe
    fun onConfigurationSuccess(configuration: Configuration) {
        NetworkingBus.unregister(this)
        configLiveData.postValue(configuration)
        AsyncTask.execute(SaveConfigurationRunnable(configuration))
    }

    @Subscribe
    fun onConfigurationFailed(apiError: ApiError) {
        // TODO
    }

}