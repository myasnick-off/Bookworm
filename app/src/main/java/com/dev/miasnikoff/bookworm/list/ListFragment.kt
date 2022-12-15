package com.dev.miasnikoff.bookworm.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.core.network.model.Volume
import com.dev.miasnikoff.bookworm.core.ui.BaseFragment
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.databinding.FragmentListBinding
import com.dev.miasnikoff.bookworm.list.adapter.VolumeListAdapter

class ListFragment: BaseFragment(), ListView {

    private lateinit var _binding: FragmentListBinding
    override val binding: FragmentListBinding
        get() = _binding

    private val presenter: ListPresenter = ListPresenterImpl()
    private val volumeListAdapter: VolumeListAdapter = VolumeListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
        initPresenter()
    }

    override fun initView() {
        binding.volumeList.adapter = volumeListAdapter
    }

    private fun initPresenter() {
        presenter.attachView(this)
        presenter.getVolumeList(DEFAULT_QUERY)
    }

    override fun showList(volumes: List<RecyclerItem>) {
        volumeListAdapter.submitList(volumes)
    }

    override fun showError(message: String) {
        //TODO("Not yet implemented")
    }

    companion object {
        private const val DEFAULT_QUERY = "+subject:Education"
        fun newInstance(): ListFragment = ListFragment()
    }
}