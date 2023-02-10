package com.dev.miasnikoff.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import com.dev.miasnikoff.core_navigation.FlowFragment
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.splash.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment(R.layout.fragment_splash), FlowFragment {


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
    }
}