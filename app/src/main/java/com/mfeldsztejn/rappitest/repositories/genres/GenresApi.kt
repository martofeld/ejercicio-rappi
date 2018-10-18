package com.mfeldsztejn.rappitest.repositories.genres

import com.mfeldsztejn.rappitest.dtos.Genre
import com.mfeldsztejn.rappitest.networking.RetrofitInstanceBuilder
import retrofit2.Call
import retrofit2.http.GET

interface GenresApi {
    @GET("genre/movie/list")
    fun obtainGenres(): Call<GenresResponse>

    companion object {
        fun build(): GenresApi {
            return RetrofitInstanceBuilder
                    .build()
                    .create(GenresApi::class.java)
        }
    }
}

data class GenresResponse(val genres: List<Genre>)