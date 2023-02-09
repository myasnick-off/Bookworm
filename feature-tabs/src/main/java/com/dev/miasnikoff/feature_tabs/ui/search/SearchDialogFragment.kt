package com.dev.miasnikoff.feature_tabs.ui.search

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import com.dev.miasnikoff.core_ui.extensions.hideSoftKeyboard
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentDialogSearchBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchDialogFragment : BottomSheetDialogFragment(R.layout.fragment_dialog_search) {

    private lateinit var binding: FragmentDialogSearchBinding

    private var searchClickListener: SearchClickListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDialogSearchBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        searchClickListener = null
        super.onDestroyView()
    }

    @SuppressLint("RestrictedApi")
    private fun initView() = with(binding) {
        searchInputLayout.setEndIconOnClickListener {
            root.hideSoftKeyboard()
            searchClickListener?.onSearchClick(phrase = searchEditText.text.toString())
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        searchClickListener?.onDialogDismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchClickListener = null
    }

    fun setOnSearchClickListener(listener: SearchClickListener) {
        searchClickListener = listener
    }

    companion object {
        fun newInstance() = SearchDialogFragment()
    }
}