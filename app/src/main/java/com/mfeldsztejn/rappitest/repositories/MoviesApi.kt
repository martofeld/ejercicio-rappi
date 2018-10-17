package com.mfeldsztejn.rappitest.repositories

import com.mfeldsztejn.rappitest.dtos.Movie
import com.mfeldsztejn.rappitest.networking.ApiResult
import com.mfeldsztejn.rappitest.networking.RetrofitInstanceBuilder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    fun discover(@Query("page") page: Int, @Query("sort_by") sortBy: String): Call<ApiResult<Movie>>

    companion object {
        fun build(): MoviesApi {
            return RetrofitInstanceBuilder
                    .build()
                    .create(MoviesApi::class.java)
        }
    }
}