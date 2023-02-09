package com.dev.miasnikoff.bookworm.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.miasnikoff.bookworm.databinding.ActivitySplashBinding
import com.dev.miasnikoff.bookworm.ui.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun startMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}