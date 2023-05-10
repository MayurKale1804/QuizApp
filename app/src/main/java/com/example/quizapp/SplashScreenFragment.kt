package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SplashScreenFragment : Fragment() {

    private lateinit var listener: SplashScreenLauncher

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        listener = activity as SplashScreenLauncher

        listener.launchSetupScreen()

        return view
    }

}