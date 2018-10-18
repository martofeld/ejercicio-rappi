package com.mfeldsztejn.rappitest.dtos.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mfeldsztejn.rappitest.dtos.Images

class ImagesConverter {
    @TypeConverter
    fun toString(images: Images): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun fromString(string: String): Images {
        return Gson().fromJson(string, Images::class.java)
    }
}

class ListConverter {
    @TypeConverter
    fun toString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(string: String): List<String> {
        return Gson().fromJson(string, TypeToken.getParameterized(List::class.java, String::class.java).type)
    }
}