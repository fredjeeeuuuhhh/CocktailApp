package com.example.cocktailapp.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun stringsToField(strings: List<String>): String {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun fieldToStrings(field: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(field,listType)
    }
}