package com.dev.miasnikoff.bookworm.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.ActivitySplashBinding
import com.dev.miasnikoff.bookworm.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val handler: Handler by lazy { Handler(mainLooper) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splashAnimation =
            AnimationUtils.loadAnimation(this, R.anim.splash_appearance_anim).apply {
                interpolator = AccelerateDecelerateInterpolator()
            }
        binding.splashImg.startAnimation(splashAnimation)
        binding.splashImg.visibility = View.VISIBLE
        handler.postDelayed(::startMainScreen, 2000)
    }

    private fun startMainScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}