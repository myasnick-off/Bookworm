package com.dev.miasnikoff.feature_tabs.ui.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev.miasnikoff.core.extensions.viewModel
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.showSnackBar
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentHomeBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.HomeListAdapter
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday.BookOfDayCell
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.CarouselWithTitleCell
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.feature_tabs.ui.home.model.HomeState
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    private val itemClickListener = object : BookOfDayCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            navigateToDetails(itemId)
        }
    }
    private val carouselClickListener = object : CarouselWithTitleCell.ItemClickListener {
        override fun onTitleClick(category: Category) {
            when (category) {
                Category.LAST_VIEWED, Category.FAVORITE -> navigateToLocalList(category.name)
                Category.POP_GENRES -> { /*todo: create genres fragment*/ }
                else -> navigateToBookList(category = category)
            }
        }

        override fun onGenreClick(query: String) {
            navigateToBookList(query = query)
        }

        override fun onBookClick(bookId: String) {
            navigateToDetails(bookId)
        }
    }
    private val homeListAdapter: HomeListAdapter =
        HomeListAdapter(itemClickListener, carouselClickListener)

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
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
    }

    override fun initMenu() {
        binding.searchButton.setOnClickListener {
            binding.searchEditText.text?.let { phrase ->
                hideSoftKeyboard()
                binding.searchEditText.text = null
                navigateToBookList(query = phrase.toString())
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
            message = "${getString(com.dev.miasnikoff.core_ui.R.string.error)} $message",
            actionText = getString(com.dev.miasnikoff.core_ui.R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { viewModel.getHomeData() }
    }

    private fun navigateToDetails(bookId: String) {
        val direction = HomeFragmentDirections.actionHomeFragmentToBookDetailsFragment(bookId)
        val controller = findNavController()
        controller.navigate(direction)
    }

    private fun navigateToLocalList(categoryName: String) {
        val direction = HomeFragmentDirections.actionHomeFragmentToLocalListFragment(categoryName)
        viewModel.navigate(direction)
    }

    private fun navigateToBookList(query: String? = null, category: Category = Category.NONE) {
        val direction = HomeFragmentDirections.actionHomeFragmentToBookListFragment(
            query = query,
            category = category
        )
        viewModel.navigate(direction)
    }
}