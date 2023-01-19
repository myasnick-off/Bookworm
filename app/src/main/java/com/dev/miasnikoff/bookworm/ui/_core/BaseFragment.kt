package com.dev.miasnikoff.bookworm.ui._core

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
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

    protected fun showAlertDialog(
        titleRes: Int,
        positiveButtonRes: Int = R.string.yes,
        negativeButtonRes: Int = R.string.no,
        action: () -> Unit
    ) {
        AlertDialog.Builder(binding.root.context)
            .setTitle(titleRes)
            .setPositiveButton(positiveButtonRes) { _, _ -> action() }
            .setNegativeButton(negativeButtonRes) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}