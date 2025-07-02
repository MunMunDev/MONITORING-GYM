package com.abcd.monitoring_gym.ui.activity.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.abcd.monitoring_gym.databinding.ActivitySplashScreenBinding
import com.abcd.monitoring_gym.ui.activity.login.LoginActivity
import com.abcd.monitoring_gym.ui.activity.user.main.MainActivity
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sharedPreferencesLogin: SharedPreferencesLogin
    private val SPLASH_DELAY = 3000L // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSharedPreferences()
        setupSplashScreen()
    }

    private fun setSharedPreferences() {
        sharedPreferencesLogin = SharedPreferencesLogin(this@SplashScreenActivity)
    }

    private fun setupSplashScreen() {
        // Start progress animation
        binding.progressBar.max = 100
        animateProgress()

        // Navigate to LoginActivity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToLogin()
        }, SPLASH_DELAY)
    }

    private fun animateProgress() {
        var progress = 0
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                progress += 2
                binding.progressBar.progress = progress
                if (progress < 100) {
                    handler.postDelayed(this, SPLASH_DELAY / 50) // Divide by 50 to make it smooth
                }
            }
        }
        handler.post(runnable)
    }

    private fun navigateToLogin() {
        if(sharedPreferencesLogin.getSebagai() == "user"){
            startActivity(Intent(this, MainActivity::class.java))
        } else if(sharedPreferencesLogin.getSebagai() == "pelatih"){
//            startActivity(Intent(this, LoginActivity::class.java))
        }  else if(sharedPreferencesLogin.getSebagai() == "admin"){
//            startActivity(Intent(this, LoginActivity::class.java))
        } else{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        startActivity(intent)
        finish() // Close splash screen activity
    }
}