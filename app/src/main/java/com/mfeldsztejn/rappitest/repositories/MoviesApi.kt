package com.mfeldsztejn.rappitest.repositories

import com.mfeldsztejn.rappitest.dtos.Movie
import com.mfeldsztejn.rappitest.dtos.MovieDetail
import com.mfeldsztejn.rappitest.networking.ApiResult
import com.mfeldsztejn.rappitest.networking.RetrofitInstanceBuilder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    fun discover(@Query("page") page: Int, @Query("sort_by") sortBy: String): Call<ApiResult<Movie>>

    @GET("movie/{movieId}?append_to_response=videos,reviews")
    fun movie(@Path("movieId") id: Long): Call<MovieDetail>

    companion object {
        fun build(): MoviesApi {
            return RetrofitInstanceBuilder
                    .build()
                    .create(MoviesApi::class.java)
        }
    }
}