package com.example.weathersooq.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.weathersooq.database.WeatherDatabase
import com.example.weathersooq.util.WEATHER_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, WeatherDatabase::class.java, WEATHER_DB).build()

    @Singleton
    @Provides
    fun provideDao(database: WeatherDatabase) = database.weatherDao()
}