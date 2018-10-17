package com.mfeldsztejn.rappitest.repositories

import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.networking.RetrofitInstanceBuilder
import retrofit2.Call
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("configuration")
    fun configuration(): Call<Configuration>

    companion object {
        fun build(): ConfigurationApi {
            return RetrofitInstanceBuilder
                    .build()
                    .create(ConfigurationApi::class.java)
        }
    }
}