package com.mfeldsztejn.rappitest.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mfeldsztejn.rappitest.dtos.Movie
import com.mfeldsztejn.rappitest.repositories.MovieDataSourceFactory
import com.mfeldsztejn.rappitest.repositories.MoviesDataSource


class ListViewModel : ViewModel() {
    lateinit var moviesPagedList: LiveData<PagedList<Movie>>

    fun discover(sortBy: String) {
        createLiveData(sortBy)
    }

    private fun createLiveData(sortBy: String) {
        val factory = MovieDataSourceFactory(sortBy)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(MoviesDataSource.PAGE_SIZE)
                .build()

        moviesPagedList = LivePagedListBuilder(factory, pagedListConfig).build()
    }
}
