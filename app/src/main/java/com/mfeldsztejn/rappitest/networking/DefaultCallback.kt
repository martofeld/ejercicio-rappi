package com.mfeldsztejn.rappitest.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefaultCallback<T> : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        NetworkingBus.post(ApiError(500, t.message!!))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            NetworkingBus.post(response.body() as Any)
        } else {
            NetworkingBus.post(ApiError(response.code(), response.message()))
        }
    }
}

data class ApiError(val statusCode: Int, val message: String)

data class ApiResult<T>(val page: Int, val totalResults: Int, val totalPages: Int, val results: List<T>)