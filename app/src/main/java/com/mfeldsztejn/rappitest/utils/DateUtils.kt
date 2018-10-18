package com.mfeldsztejn.rappitest.utils

import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {
    companion object {

        fun isOneMonthOld(date: Long): Boolean {
            val oneMonthMillis = TimeUnit.DAYS.toMillis(31)
            return Date().time - oneMonthMillis > date
        }
    }
}