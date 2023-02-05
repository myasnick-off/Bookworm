package com.dev.miasnikoff.bookworm.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentHomeBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.ViewModelFactory
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.details.BookDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.adapter.HomeListAdapter
import com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday.BookOfDayCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.CarouselWithTitleCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.home.model.HomeState
import com.dev.miasnikoff.bookworm.ui.list.BookListFragment
import com.dev.miasnikoff.bookworm.ui.list.LocalListFragment
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private val itemClickListener = object : BookOfDayCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            openFragment(fragment = BookDetailsFragment.newInstance(itemId))
        }
    }
    private val carouselClickListener = object : CarouselWithTitleCell.ItemClickListener {
        override fun onTitleClick(category: Category) {
            when (category) {
                Category.LAST_VIEWED, Category.FAVORITE ->
                    openFragment(fragment = LocalListFragment.newInstance(category = category))
                Category.POP_GENRES -> {}
                else -> openFragment(fragment = BookListFragment.newInstance(category = category))
            }
        }

        override fun onGenreClick(query: String) {
            openFragment(fragment = BookListFragment.newInstance(query = query))
        }

        override fun onBookClick(bookId: String) {
            openFragment(fragment = BookDetailsFragment.newInstance(bookId))
        }

    }
    private val homeListAdapter: HomeListAdapter = HomeListAdapter(itemClickListener, carouselClickListener)

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
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