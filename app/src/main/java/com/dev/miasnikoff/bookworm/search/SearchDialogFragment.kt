package com.dev.miasnikoff.bookworm.search

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.miasnikoff.bookworm.databinding.FragmentDialogSearchBinding
import com.dev.miasnikoff.bookworm.utils.extensions.hideSoftKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchDialogFragment : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentDialogSearchBinding
    private val binding get() = _binding
    private var searchClickListener: SearchClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogSearchBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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