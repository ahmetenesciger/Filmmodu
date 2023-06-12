package com.exampla.redlauncher.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*



class MyCustomClassConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromListString(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toListString(value: String?): List<String>? {
        if (value == null) return null

        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

}