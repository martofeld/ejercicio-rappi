package com.mfeldsztejn.rappitest.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mfeldsztejn.rappitest.dtos.MovieDetail
import com.mfeldsztejn.rappitest.networking.ApiError
import com.mfeldsztejn.rappitest.networking.DefaultCallback
import com.mfeldsztejn.rappitest.networking.NetworkingBus
import com.mfeldsztejn.rappitest.repositories.MoviesApi
import org.greenrobot.eventbus.Subscribe

class DetailViewModel : ViewModel() {

    private val moviesApi = MoviesApi.build()
    private var movieId: Long = 0

    private val _movieLiveData = MutableLiveData<MovieDetail>()
    val movieLiveData: LiveData<MovieDetail>
        get() = _movieLiveData

    fun setMovieId(id: Long) {
        if (movieId != 0L) {
            return
        }
        movieId = id
        NetworkingBus.register(this)
        moviesApi
                .movie(id)
                .enqueue(DefaultCallback<MovieDetail>())
    }

    @Subscribe
    fun onMovieDetailObtained(movie: MovieDetail) {
        NetworkingBus.unregister(this)
        _movieLiveData.value = movie
    }

    @Subscribe
    fun onMovieDetailFailed(apiError: ApiError) {
        Log.e("ERROR", apiError.message)
    }
}
