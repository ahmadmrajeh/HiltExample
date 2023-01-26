package com.example.weathersooq.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
 import androidx.navigation.fragment.findNavController
import com.example.weathersooq.R
import com.example.weathersooq.databinding.FragmentViewPagerBinding


class ViewPagerFragment : Fragment() {
    lateinit var binding: FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
              binding = FragmentViewPagerBinding.inflate(inflater)
        binding.imageButton.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)
        }
        val numberOfScreens = 2
      val onBoardingAdapter = OnBoardingAdapter(requireActivity(), numberOfScreens)

binding.viewPager.adapter=onBoardingAdapter

        val tabLayout =  binding.tabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) {
                tab, position ->

        }.attach()


        return binding.root
    }

}