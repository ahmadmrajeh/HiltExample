package com.example.weathersooq.repository
import com.example.weathersooq.data.WeatherModel
import com.example.weathersooq.database.WeatherDAO
import com.example.weathersooq.database.WeatherEntity
import com.example.weathersooq.retrofit.ApiRequests
import dagger.hilt.android.scopes.ActivityRetainedScoped

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val apiRequests: ApiRequests,
    private val weatherDAO: WeatherDAO

    ) {

    //room
    fun getWeatherData(): Flow<List<WeatherEntity>>    {
        return weatherDAO.readWeather()
    }

suspend fun insertWeatherCity (weather: WeatherEntity){
    weatherDAO.insertCity(weather)
}

    //retrofit
    suspend fun searchWeatherByCity(query: String):Response<WeatherModel> {
        return apiRequests.getWeatherTown(query)
    }

}