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
    var query: String? = null

    fun discover(sortBy: String) {
        createLiveData(sortBy, null)
    }

    private fun createLiveData(sortBy: String, query: String?) {
        val factory = MovieDataSourceFactory(sortBy, query)

        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(MoviesDataSource.PAGE_SIZE)
                .build()

        moviesPagedList = LivePagedListBuilder(factory, pagedListConfig).build()
    }

    fun search(sortBy: String, query: String?): Boolean {
        if (this.query == query) {
            return false
        }
        this.query = query
        createLiveData(sortBy, query)
        return true
    }
}
