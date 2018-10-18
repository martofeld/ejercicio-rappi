package com.mfeldsztejn.rappitest.dtos

import com.mfeldsztejn.rappitest.networking.ApiResult
import java.util.*

data class MovieDetail(val id: Long, val title: String, val posterPath: String,
                       val backdropPath: String?, val overview: String,
                       val releaseDate: Date?, val videos: ApiResult<Video>?,
                       val tagline: String, val voteAverage: Float, val voteCount: Int,
                       val status: String, val reviews: ApiResult<Review>, val genres: List<Genre>)
