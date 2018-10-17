package com.mfeldsztejn.rappitest.networking

import okhttp3.Interceptor
import okhttp3.Response

abstract class QueryParamInterceptor : Interceptor {

    abstract fun getKey(): String
    abstract fun getValue(): String

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()
                .newBuilder()
                .addQueryParameter(getKey(), getValue())
                .build()

        return chain.proceed(
                request.newBuilder()
                        .url(url)
                        .build())
    }
}