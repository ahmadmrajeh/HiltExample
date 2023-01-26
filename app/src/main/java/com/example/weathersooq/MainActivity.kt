package com.example.weathersooq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.weathersooq.databinding.ActivityMainBinding
import com.example.weathersooq.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        mainViewModel = ViewModelProvider(this)  [MainViewModel::class.java]



       binding.bottomNavigation.visibility= View.INVISIBLE

        setContentView(binding.root)


        mainViewModel.bottomNavInit(  mainActivity = this,binding)


        //  supportActionBar?.hide()

    }

}

