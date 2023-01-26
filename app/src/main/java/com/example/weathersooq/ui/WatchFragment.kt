package com.example.weathersooq.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weathersooq.R
import com.example.weathersooq.database.WeatherEntity
import com.example.weathersooq.databinding.FragmentWatchBinding
import com.example.weathersooq.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchFragment : Fragment() {
    private lateinit var binding: FragmentWatchBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchBinding.inflate(inflater)




        readLocalData()





        return binding.root
    }

    private fun readLocalData() {
        lifecycleScope.launch {
            mainViewModel.readWeather.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {


                    setAmmanWeather(database)

                    setIrbidWeather(database)


                }
            }
        }

    }

    private fun setIrbidWeather(database: List<WeatherEntity>) {
        binding.cityName2.text = getString(R.string.Irbid)
        binding.minTemp2.text = database[1].mintempC
        binding.avgTemp2.text = database[1].avgtempC
        binding.maxTemp2.text = database[1].maxtempC
    }

    private fun setAmmanWeather(database: List<WeatherEntity>) {
        binding.cityName.text = getString(R.string.Amman)
        binding.minTemp.text = database[0].mintempC
        binding.avgTemp.text = database[0].avgtempC
        binding.maxTemp.text = database[0].maxtempC
    }

}