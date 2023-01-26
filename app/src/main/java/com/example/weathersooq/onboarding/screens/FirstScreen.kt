package com.example.weathersooq.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.weathersooq.R
import com.example.weathersooq.databinding.FragmentFirstScreenBinding


class FirstScreen : Fragment() {

    companion object {
        private const val ARG_POSITION = "ARG_POSITION"

        fun getInstance(position: Int) = FirstScreen().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }


    lateinit var binding: FragmentFirstScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstScreenBinding.inflate(inflater)


        onBoardingFinished()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt(ARG_POSITION)

        with(binding) {

            val selectedMainImage = when (position) {
             0 -> R.drawable.find
                1->R.drawable.play
                else -> {R.drawable.find}
            }
          imageView.setImageResource(selectedMainImage)

            val selectShortText = when (position) {
                0 -> R.drawable.word_one
                1->R.drawable.see
                else -> {R.drawable.word_one}
            }
            imageView6.setImageResource(selectShortText)

            val selectLongText = when (position) {
                0 -> {
                    "Dicover The Weather in any City\n (slider appears once)"
                }
                1-> {
                    "Look at how cold/hot it feels\ntoday in Amman and Irbid"
                }
                else -> {
                    "R.drawable.word_one"
                }
            }
            textView.text =selectLongText

        }
    }


    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}