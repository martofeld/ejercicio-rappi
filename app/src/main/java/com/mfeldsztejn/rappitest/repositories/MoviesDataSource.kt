package com.mfeldsztejn.rappitest.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.mfeldsztejn.rappitest.dtos.Movie
import com.mfeldsztejn.rappitest.networking.ApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesDataSource(private val sortBy: String, private val query: String?) : PageKeyedDataSource<Int, Movie>() {

    private val moviesApi: MoviesApi = MoviesApi.build()

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 50
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        getCall(FIRST_PAGE).enqueue(object : Callback<ApiResult<Movie>> {
            override fun onFailure(call: Call<ApiResult<Movie>>, t: Throwable) {
                Log.e(this::class.java.simpleName, "", t)
            }

            override fun onResponse(call: Call<ApiResult<Movie>>, response: Response<ApiResult<Movie>>) {
                if (response.body() != null) {
                    callback.onResult(response.body()!!.results, null, FIRST_PAGE + 1)
                }
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        getCall(params.key).enqueue(object : Callback<ApiResult<Movie>> {
            override fun onFailure(call: Call<ApiResult<Movie>>, t: Throwable) {
                Log.e(this::class.java.simpleName, "", t)
            }

            override fun onResponse(call: Call<ApiResult<Movie>>, response: Response<ApiResult<Movie>>) {
                if (response.body() != null) {
                    val key = if (response.body()!!.totalPages >= params.key) params.key + 1 else null
                    callback.onResult(response.body()!!.results, key)
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        getCall(params.key).enqueue(object : Callback<ApiResult<Movie>> {
            override fun onFailure(call: Call<ApiResult<Movie>>, t: Throwable) {
                Log.e(this::class.java.simpleName, "", t)
            }

            override fun onResponse(call: Call<ApiResult<Movie>>, response: Response<ApiResult<Movie>>) {
                val adjacentKey = if (params.key > 1) params.key - 1 else null
                if (response.body() != null) {
                    callback.onResult(response.body()!!.results, adjacentKey)
                }
            }
        })
    }

    private fun getCall(page: Int): Call<ApiResult<Movie>> {
        return if (query.isNullOrEmpty()) moviesApi.discover(page, sortBy) else moviesApi.search(page, sortBy, query!!)
    }
}

class MovieDataSourceFactory(private val sortBy: String, private val query: String?) : DataSource.Factory<Int, Movie>() {

    private val _dataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    val dataSource: LiveData<PageKeyedDataSource<Int, Movie>>
        get() = _dataSource

    override fun create(): DataSource<Int, Movie> {
        val dataSource = MoviesDataSource(sortBy, query)

        _dataSource.postValue(dataSource)

        return dataSource
    }

}