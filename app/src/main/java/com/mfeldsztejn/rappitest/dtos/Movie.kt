package com.mfeldsztejn.rappitest.dtos

import java.io.Serializable
import java.util.*

data class Movie(val id: Long, val title: String, val posterPath: String,
                 val backdropPath: String?, val overview: String, val genreIds: List<Int>,
                 val releaseDate: Date?)
    : Serializable