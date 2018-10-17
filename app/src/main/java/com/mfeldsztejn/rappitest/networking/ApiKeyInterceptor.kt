package com.mfeldsztejn.rappitest.networking


class ApiKeyInterceptor : QueryParamInterceptor() {

    companion object {
        const val API_KEY_PARAM: String = "api_key"
    }

    override fun getKey() = API_KEY_PARAM

    override fun getValue() = "a93f910873b4805d6183cc0599d5879e"
}