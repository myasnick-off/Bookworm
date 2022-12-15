package com.dev.miasnikoff.bookworm.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.network.model.Volume
import com.dev.miasnikoff.bookworm.core.ui.BaseFragment
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.databinding.FragmentListBinding
import com.dev.miasnikoff.bookworm.list.adapter.VolumeListAdapter
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar

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
        getData()
    }

    override fun showList(volumes: List<RecyclerItem>) {
        binding.listLoader.visibility = View.GONE
        volumeListAdapter.submitList(volumes)
    }

    override fun showError(message: String) {
        binding.listLoader.visibility = View.GONE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload)
        ) { getData() }
    }

    private fun getData(query: String = DEFAULT_QUERY) {
        binding.listLoader.visibility = View.VISIBLE
        presenter.getVolumeList(query)
    }

    companion object {
        private const val DEFAULT_QUERY = "+subject:Education"
        fun newInstance(): ListFragment = ListFragment()
    }
}