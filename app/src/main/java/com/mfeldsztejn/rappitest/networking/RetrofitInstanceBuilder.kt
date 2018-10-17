package com.mfeldsztejn.rappitest.networking

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.mfeldsztejn.rappitest.MainApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

class RetrofitInstanceBuilder {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        fun build(): Retrofit {
            val httpCacheDirectory = File(MainApplication.application.cacheDir, "responses")
            val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
            val cache = Cache(httpCacheDirectory, cacheSize)
            val client = OkHttpClient.Builder()
                    .addInterceptor(ApiKeyInterceptor())
                    .addInterceptor(LanguageInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .cache(cache)
                    .build()

            val gson = GsonBuilder()
                    .registerTypeAdapter(Date::class.java, DateDeserializer())
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit
        }
    }
}