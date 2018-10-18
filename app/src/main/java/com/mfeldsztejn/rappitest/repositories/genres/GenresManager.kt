package com.mfeldsztejn.rappitest.repositories.genres

import android.content.Context
import android.os.AsyncTask
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mfeldsztejn.rappitest.AppDatabase
import com.mfeldsztejn.rappitest.Constants
import com.mfeldsztejn.rappitest.MainApplication
import com.mfeldsztejn.rappitest.dtos.Genre
import com.mfeldsztejn.rappitest.networking.DefaultCallback
import com.mfeldsztejn.rappitest.networking.NetworkingBus
import com.mfeldsztejn.rappitest.utils.DateUtils
import org.greenrobot.eventbus.Subscribe

class GenresManager(val context: Context) {

    fun loadGenres(): LiveData<Map<Int, Genre>> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.application)
        if (DateUtils.isOneMonthOld(preferences.getLong(Constants.GENRES_LAST_UPDATE, 0))) {
            NetworkingBus.register(this)
            GenresApi.build()
                    .obtainGenres()
                    .enqueue(DefaultCallback<GenresResponse>())
        }
        return Transformations.map(AppDatabase
                .getInstance()
                .genreDao()
                .findAll()) { genres ->
            genres.associateBy { it.id }
        }
    }

    @Subscribe
    fun onGenresObtained(response: GenresResponse) {
        NetworkingBus.unregister(this)
        AsyncTask.execute {
            AppDatabase.getInstance()
                    .genreDao()
                    .insertAll(response.genres)
        }
    }
}