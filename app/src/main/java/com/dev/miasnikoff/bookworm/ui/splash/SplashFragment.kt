package com.dev.miasnikoff.bookworm.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentSplashBinding
import com.dev.miasnikoff.core_ui.BaseFragment

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val handler: Handler by lazy { Handler(requireActivity().mainLooper) }
    override lateinit var binding: FragmentSplashBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        initView()
    }

    override fun initView() {
        val splashAnimation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.splash_appearance_anim).apply {
                interpolator = AccelerateDecelerateInterpolator()
            }
        binding.splashImg.startAnimation(splashAnimation)
        binding.splashImg.visibility = View.VISIBLE
        handler.postDelayed(::startMainScreen, 2000)
    }

    private fun startMainScreen() {
        (requireActivity() as? SplashActivity)?.startMainScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}