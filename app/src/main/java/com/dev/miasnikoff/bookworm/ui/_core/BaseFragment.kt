package com.dev.miasnikoff.bookworm.ui._core

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.viewbinding.ViewBinding
import com.dev.miasnikoff.bookworm.R

abstract class BaseFragment : Fragment() {

    protected abstract val binding: ViewBinding

    open fun initMenu() {}

    protected abstract fun initView()

    protected fun navigateToFragment(
        containerId: Int = R.id.host_container,
        fragment: Fragment,
        isAddToBackStack: Boolean = false
    ) {
        childFragmentManager.commit(allowStateLoss = true) {
            replace(containerId, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            if (isAddToBackStack) addToBackStack(null)
        }
    }

    protected fun openFragment(
        container: Int = R.id.main_container,
        fragment: Fragment,
        isAddToBackStack: Boolean = true
    ) {
        requireActivity().supportFragmentManager.commit(allowStateLoss = true) {
            add(container, fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (isAddToBackStack) addToBackStack(null)
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