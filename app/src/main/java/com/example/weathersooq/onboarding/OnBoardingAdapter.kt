package com.example.weathersooq.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weathersooq.onboarding.screens.FirstScreen

class OnBoardingAdapter(activity: FragmentActivity, private val itemsCount: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return FirstScreen.getInstance(position)
    }
}