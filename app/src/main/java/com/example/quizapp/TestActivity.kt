package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TestActivity : AppCompatActivity(), ButtonClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

    }

    override fun startQuiz() {

    }

    override fun launchDetailsFragment() {

    }

    override fun launchSummaryFragment() {
        val fragment = SummaryFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun restartQuiz() {

    }
}