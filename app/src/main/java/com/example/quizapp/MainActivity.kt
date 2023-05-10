package com.example.quizapp

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity(), SplashScreenLauncher, ButtonClickListener {

    private lateinit var viewModel: QuestionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(QuestionsViewModel::class.java)

        if (savedInstanceState == null) {
            launchSplashScreen()
        }

        val counterReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                viewModel.setTime(p1?.getLongExtra(ServiceForTimer.KEY, -1) ?: -1)
                viewModel.autoSubmit.value = p1?.getBooleanExtra(ServiceForTimer.FINISH_KEY, false)
            }
        }

        val filter = IntentFilter(ServiceForTimer.PACKAGE)
        registerReceiver(counterReceiver, filter)

        viewModel.autoSubmit.observe(this, Observer {
            if (it) {
                launchSummaryFragment()
            }
        })

    }

    //  Function to launch splashScreen
    private fun launchSplashScreen() {
        val fragment = SplashScreenFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    //  Function to launch SetupScreen
    override fun launchSetupScreen() {
        Handler(Looper.getMainLooper()).postDelayed({
            val fragment = SetupScreenFragment()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
        }, 1500)
    }

    //  Function to start the quiz on clicking start button on setupScreen
    override fun startQuiz() {
        if (!applicationContext.isMyServiceRunning(ServiceForTimer::class.java)) {
            startService()
        }
        val fragment = QuestionsListFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    //  On clicking any item on recyclerview this function is called to launch QuestionDetailScreen with the position of item clicked
    override fun launchDetailsFragment() {
        val fragment = QuestionDetailsFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit()
    }

    //  Function to launch SummaryScreen and stop the time service
    override fun launchSummaryFragment() {
        stopService()
        val fragment = SummaryFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    //  Function to start service again on clicking the restart button on SummaryScreen
    override fun restartQuiz() {
        viewModel.reinitializeData()
        val fragment = QuestionsListFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
        startService()
    }

    //  Function to start the time service
    private fun startService() {
        val serviceIntent = Intent(this, ServiceForTimer::class.java)
        startService(serviceIntent)
    }

    //  Function to stop the service
    private fun stopService() {
        val serviceIntent = Intent(this, ServiceForTimer::class.java)
        stopService(serviceIntent)
    }

    private fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE)
            .any { it.service.className == serviceClass.name }
    }
}