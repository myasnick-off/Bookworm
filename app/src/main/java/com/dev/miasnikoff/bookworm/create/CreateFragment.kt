package com.dev.miasnikoff.bookworm.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.miasnikoff.bookworm.edit.EditFragment
import com.dev.miasnikoff.bookworm.core.BaseFragment
import com.dev.miasnikoff.bookworm.databinding.FragmentCreateBinding

class CreateFragment: BaseFragment() {

    private lateinit var _binding: FragmentCreateBinding
    override val binding: FragmentCreateBinding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun initView() {
        binding.createButton.setOnClickListener {
            navigateToFragment(EditFragment.newInstance())
        }
    }

    companion object {
        fun newInstance(): CreateFragment = CreateFragment()
    }
}