package com.dev.miasnikoff.bookworm.core

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.dev.miasnikoff.bookworm.R

abstract class BaseFragment: Fragment() {

    protected abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
    }

    open fun initMenu() {}

    protected abstract fun initView()

    protected fun navigateToFragment(container: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        if (isAddToBackStack) {
            parentFragmentManager.beginTransaction()
                .replace(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.host_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    protected fun openFragment(container: Int, fragment: Fragment, isAddToBackStack: Boolean = false) {
        if (isAddToBackStack) {
            parentFragmentManager.beginTransaction()
                .add(container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        } else {
            parentFragmentManager.beginTransaction()
                .add(R.id.host_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }

    protected fun hideSoftKeyboard() {
        binding.root.findFocus()?.clearFocus()
        view?.let { view ->
            val imm =
                requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}