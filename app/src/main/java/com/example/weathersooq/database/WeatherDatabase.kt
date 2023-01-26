package com.example.weathersooq.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weathersooq.data.Weather

@Database(
    entities = [WeatherEntity::class ] ,
    version = 1,
    exportSchema = false
)

@TypeConverters(TypeConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDAO
}
