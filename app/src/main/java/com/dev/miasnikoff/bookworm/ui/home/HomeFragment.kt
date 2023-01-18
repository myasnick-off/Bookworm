package com.dev.miasnikoff.bookworm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentHomeBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.details.VolumeDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.adapter.HomeListAdapter
import com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday.BookOfDayCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.CarouselWithTitleCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.home.model.HomeState
import com.dev.miasnikoff.bookworm.ui.list.BookListFragment
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment() {

    private lateinit var _binding: FragmentHomeBinding
    override val binding: FragmentHomeBinding
        get() = _binding

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }
    private val itemClickListener = object : BookOfDayCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            openFragment(fragment = VolumeDetailsFragment.newInstance(itemId))
        }
    }
    private val carouselClickListener = object: CarouselWithTitleCell.ItemClickListener {
        override fun onTitleClick(category: Category) {
            if (category != Category.POP_GENRES) {
                openFragment(fragment = BookListFragment.newInstance(category = category))
            }
        }

        override fun onGenreClick(query: String) {
            openFragment(fragment = BookListFragment.newInstance(query = query))
        }

        override fun onBookClick(bookId: String) {
            openFragment(fragment = VolumeDetailsFragment.newInstance(bookId))
        }

    }
    private val homeListAdapter: HomeListAdapter = HomeListAdapter(itemClickListener, carouselClickListener)

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
        binding.homeList.adapter = homeListAdapter
        binding.searchButton.setOnClickListener {
            binding.searchEditText.text?.let { phrase ->
                hideSoftKeyboard()
                binding.searchEditText.text = null
                openFragment(fragment = BookListFragment.newInstance(query = phrase.toString()))
            }
        }
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
        binding.homeList.visibility = View.GONE
    }

    private fun showData(data: List<RecyclerItem>) {
        binding.homeLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.homeList.visibility = View.VISIBLE
        homeListAdapter.submitList(data)
    }

    private fun showError(message: String) {
        binding.homeLoader.visibility = View.GONE
        binding.homeList.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { viewModel.getHomeData() }
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}