package com.example.weathersooq.ui.viewModels

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import androidx.core.view.isNotEmpty
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.weathersooq.MainActivity
import com.example.weathersooq.R
import com.example.weathersooq.data.Weather
import com.example.weathersooq.data.WeatherModel
import com.example.weathersooq.database.WeatherEntity
import com.example.weathersooq.databinding.ActivityMainBinding
import com.example.weathersooq.repository.Repository
import com.example.weathersooq.util.NetworkResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private lateinit var navController: NavController

    /**  Room Database  */

    val readWeather: LiveData<List<WeatherEntity>> = repository.getWeatherData().asLiveData()


  private fun insertCity(weatherToEntity: Weather,
                         cityName:String) =
        viewModelScope.launch(Dispatchers.IO) {

            repository.insertWeatherCity(
                WeatherEntity(weatherToEntity.avgtempC,
                weatherToEntity.maxtempC,weatherToEntity.mintempC,
                    cityName)
            )


        }



    /**  Retrofit  */
   var weatherResponse: MutableLiveData<NetworkResult<WeatherModel>> = MutableLiveData()


    fun readTownWeather(queries: String) = viewModelScope.launch {
        getWeatherSafeCall(queries)
    }

    private suspend fun getWeatherSafeCall(queries: String) {
         weatherResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.searchWeatherByCity(queries)
                weatherResponse.value = handleWeatherResponse(response)

                val currentWeatherResponse =   weatherResponse.value!!.data
                 if (currentWeatherResponse != null) {
                  offlineCacheWeather(currentWeatherResponse,queries)
                }
            } catch (e: Exception) {

                weatherResponse.value = NetworkResult.Error("City not found.")
            }
        } else {

            weatherResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }


    private fun offlineCacheWeather(weatherModel: WeatherModel,townName: String) {

       insertCity(weatherModel.data.weather[0], townName )
    }


    private fun handleWeatherResponse(response: Response<WeatherModel>): NetworkResult<WeatherModel> {

        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.data.weather.isNullOrEmpty() -> {
                return NetworkResult.Error("City not found.")
            }
            response.isSuccessful -> {

                return NetworkResult.Success(response.body()!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }
    
    private fun hasInternetConnection(): Boolean {

        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false

        }
    }

    fun bottomNavInit(
        mainActivity: MainActivity,
        binding: ActivityMainBinding
    ) {
         navController = Navigation.findNavController(mainActivity, R.id.fragment)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
       // binding.bottomNavigation.visibility = View.INVISIBLE
    }



    fun setNavColor(activity: FragmentActivity) {
        val navBar: BottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)

       try {
if (navBar.isNotEmpty())
           navBar.setBackgroundColor(Color.WHITE)

       } catch(e: Exception){
 Log.e("excep", e.message.toString())
       }
        navBar.visibility = View.VISIBLE

    }


}