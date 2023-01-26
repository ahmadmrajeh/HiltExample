package com.example.weathersooq.database

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import androidx.room.TypeConverter
class TypeConverter {
    var gson = Gson()


    @TypeConverter
    fun fromStringWeather (value: String?): WeatherEntity {
        val listType = object :TypeToken<WeatherEntity>(){}.type

        return Gson().fromJson(value,listType)
    }


    @TypeConverter
    fun fromWeatherEntity (serX: WeatherEntity?):String{

        return Gson().toJson(serX)
    }



}