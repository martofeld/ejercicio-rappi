package com.mfeldsztejn.rappitest.networking

import java.util.*

class LanguageInterceptor : QueryParamInterceptor() {

    companion object {
        const val LANGUAGE_PARAM_KEY = "language"
    }

    override fun getKey() = LANGUAGE_PARAM_KEY

    override fun getValue() = Locale.getDefault().toString()

}