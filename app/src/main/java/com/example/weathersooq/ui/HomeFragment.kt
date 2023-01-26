package com.example.weathersooq.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weathersooq.R
import com.example.weathersooq.databinding.FragmentHomeBinding
import com.example.weathersooq.ui.viewModels.MainViewModel
import com.example.weathersooq.util.NetworkResult


import android.widget.Toast
import com.example.weathersooq.data.WeatherModel


import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    companion object {
        fun getInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())  [MainViewModel::class.java]


    }

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

binding.cityName.text = "City"
        binding.lifecycleOwner = this
        mainViewModel.readTownWeather(getString(R.string.Amman))
        mainViewModel.readTownWeather(getString(R.string.Irbid))

       mainViewModel.setNavColor(requireActivity())

   binding.button2.setOnClickListener {
    val city =   binding.search.text.toString()
       handleSearch(city)

   }

        return binding.root
    }

    private fun handleSearch(city: String) {
        if (city.isNotEmpty()) {

            mainViewModel.readTownWeather(city)
        }

        mainViewModel.weatherResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    response.data?.let {
                        setWeatherValues(city, it)

                    }

                }

                is NetworkResult.Error -> {

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun setWeatherValues(city: String, it: WeatherModel) {
        binding.cityName.text = city
        val lastIndex = it.data.weather.lastIndex
        binding.maxTemp.text = it.data.weather[lastIndex].maxtempC
        binding.avgTemp.text = it.data.weather[lastIndex].avgtempC
        binding.minTemp.text = it.data.weather[lastIndex].mintempC
    }

}

