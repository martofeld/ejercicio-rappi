package com.mfeldsztejn.rappitest

import android.app.Application
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.events.ConfigurationObtainedEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainApplication : Application() {

    companion object {
        var configuration: Configuration? = null
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()

        MainApplication.application = this
        ConfigurationManager(this).loadConfiguration()
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun onConfigurationObtainedEvent(event: ConfigurationObtainedEvent) {
        configuration = event.configuration
    }
}