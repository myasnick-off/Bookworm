package com.dev.miasnikoff.bookworm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentHomeBinding
import com.dev.miasnikoff.bookworm.presentation._core.BaseFragment
import com.dev.miasnikoff.bookworm.presentation.details.VolumeDetailsFragment
import com.dev.miasnikoff.bookworm.presentation.home.adapter.HomeBooksAdapter
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeBookItem
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeData
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeState
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment() {

    private lateinit var _binding: FragmentHomeBinding
    override val binding: FragmentHomeBinding
        get() = _binding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private val itemClickListener = object : HomeBooksAdapter.ItemClickListener {
        override fun onItemClick(itemId: String) {
            openFragment(fragment = VolumeDetailsFragment.newInstance(itemId))
        }
    }
    private val lastSeenAdapter: HomeBooksAdapter = HomeBooksAdapter(itemClickListener)
    private val newestAdapter: HomeBooksAdapter = HomeBooksAdapter(itemClickListener)
    private val popFreeAdapter: HomeBooksAdapter = HomeBooksAdapter(itemClickListener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMenu()
        initViewModel()
    }

    override fun initView() {
        binding.newestList.adapter = newestAdapter
        binding.lastList.adapter = lastSeenAdapter
        binding.popularList.adapter = popFreeAdapter
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: HomeState) {
        when (state) {
            is HomeState.Loading -> showLoading()
            is HomeState.Failure -> showError(state.message)
            is HomeState.Success -> showData(state.data)
        }
    }

    private fun showLoading() {
        binding.homeLoader.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.homeLayout.visibility = View.GONE
    }

    private fun showData(data: HomeData) {
        binding.homeLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.homeLayout.visibility = View.VISIBLE
        bindData(data)
    }

    private fun showError(message: String) {
        binding.homeLoader.visibility = View.GONE
        binding.homeLayout.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { viewModel.getHomeData() }
    }

    private fun bindData(data: HomeData) {
        (data.bookOfDay as? HomeBookItem)?.let { bookOfDay ->
            binding.bookOfDay.bookTitle.text = bookOfDay.title
            binding.bookOfDay.bookAuthors.text = bookOfDay.authors
            binding.bookOfDay.publisher.text = bookOfDay.publisher
            binding.bookOfDay.publishedDate.text = bookOfDay.publishedDate
            binding.bookOfDay.ratingBar.rating = bookOfDay.averageRating
            Glide.with(binding.bookOfDay.bookImage.context)
                .load(bookOfDay.imageLink)
                .error(R.drawable.ic_broken_image_48)
                .into(binding.bookOfDay.bookImage)
            binding.bookOfDay.root.setOnClickListener {
                openFragment(fragment = VolumeDetailsFragment.newInstance(data.bookOfDay.id))
            }
        }
        lastSeenAdapter.submitList(data.lastSeen)
        newestAdapter.submitList(data.newest)
        popFreeAdapter.submitList(data.popularFree)
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}