package com.mfeldsztejn.rappitest.networking

import com.mfeldsztejn.rappitest.Constants


class ApiKeyInterceptor : QueryParamInterceptor() {

    companion object {
        const val API_KEY_PARAM: String = "api_key"
    }

    override fun getKey() = API_KEY_PARAM

    override fun getValue() = Constants.TMDB_API_KEY
}