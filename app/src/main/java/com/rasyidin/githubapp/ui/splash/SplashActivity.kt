package com.rasyidin.githubapp.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.rasyidin.githubapp.core.utils.Constants.SPLASH_DELAY
import com.rasyidin.githubapp.databinding.ActivitySplashBinding
import com.rasyidin.githubapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashDelay()
    }

    private fun splashDelay() {
        val intent = Intent(this, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, SPLASH_DELAY)
    }
}