package com.dev.miasnikoff.bookworm.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.edit.EditFragment
import com.dev.miasnikoff.bookworm.core.ui.BaseFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
    }

    override fun initView() {
        binding.createButton.setOnClickListener {
            navigateToFragment(R.id.host_container, EditFragment.newInstance())
        }
        binding.leafView.setCounter(LEAF_INIT_VALUE)
        binding.leafView.setMaxValue(LEAF_MAX_VALUE)
    }

    companion object {
        private const val LEAF_INIT_VALUE = 100
        private const val LEAF_MAX_VALUE = 500
        fun newInstance(): CreateFragment = CreateFragment()
    }
}