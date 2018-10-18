package com.mfeldsztejn.rappitest

import android.app.Application
import androidx.lifecycle.LiveData
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.dtos.Genre
import com.mfeldsztejn.rappitest.repositories.configurations.ConfigurationManager
import com.mfeldsztejn.rappitest.repositories.genres.GenresManager

class MainApplication : Application() {

    companion object {
        lateinit var configuration: LiveData<Configuration>
        lateinit var genres: LiveData<Map<Int, Genre>>
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()

        MainApplication.application = this
        AppDatabase.init(this)

        configuration = ConfigurationManager(this).loadConfiguration()
        genres = GenresManager(this).loadGenres()
    }
}