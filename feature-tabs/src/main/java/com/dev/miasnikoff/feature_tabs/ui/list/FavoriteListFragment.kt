package com.dev.miasnikoff.feature_tabs.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.showAlertDialog
import com.dev.miasnikoff.core_ui.extensions.showSnackBar
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentListBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookCell
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookListAdapter
import com.dev.miasnikoff.feature_tabs.ui.list.model.PagedListState
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoriteListFragment : BaseFragment(R.layout.fragment_list) {

    override lateinit var binding: FragmentListBinding
    override val titleRes = R.string.favorite
    override val hasRemoveAllButton = true
    override val removeAllAction: () -> Unit
        get() = {
            showAlertDialog(R.string.remove_favorites_warning) { viewModel.removeAllFavorite() }
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override val viewModel: FavoriteViewModel by viewModels { viewModelFactory }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            navigateToDetails(itemId)
        }

        override fun onItemLongClick(itemId: String) {
            viewModel.removeFromFavorite(itemId)
        }

        override fun onFavoriteClick(itemId: String) {
            viewModel.removeFromFavorite(itemId)
        }
    }

    private val bookListAdapter = BookListAdapter(itemClickListener = itemClickListener)

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        initView()
        initMenu()
        initViewModel()
    }

    override fun initView() {
        super.initView()
        binding.contentRecycler.adapter = bookListAdapter
        binding.listFab.visibility = View.GONE
    }

    private fun initViewModel() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: PagedListState) {
        when (state) {
            is PagedListState.Empty -> showEmpty()
            is PagedListState.Loading -> showLoading()
            is PagedListState.MoreLoading -> showMoreLoading()
            is PagedListState.Failure -> showError(state.message)
            is PagedListState.Success -> showData(state.data, state.loadMore)
        }
    }

    private fun showEmpty() {
        binding.listLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.contentRecycler.visibility = View.GONE
        binding.emptyResult.root.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.listLoader.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.contentRecycler.visibility = View.GONE
        binding.emptyResult.root.visibility = View.GONE
    }

    private fun showMoreLoading() {
        binding.listLoader.visibility = View.VISIBLE
        binding.contentRecycler.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.emptyResult.root.visibility = View.GONE
    }

    private fun showData(volumes: List<RecyclerItem>, loadMore: Boolean) {
        binding.listLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.emptyResult.root.visibility = View.GONE
        binding.contentRecycler.visibility = View.VISIBLE
        bookListAdapter.updateList(volumes, loadMore)
    }

    private fun showError(message: String) {
        binding.listLoader.visibility = View.GONE
        binding.contentRecycler.visibility = View.GONE
        binding.emptyResult.root.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(com.dev.miasnikoff.core_ui.R.string.error)} $message",
            actionText = getString(com.dev.miasnikoff.core_ui.R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { getData() }
    }

    private fun getData() {
        viewModel.getInitialPage()
    }

    private fun navigateToDetails(bookId: String) {
        val direction =
            FavoriteListFragmentDirections.actionFavoriteListFragmentToBookDetailsFragment(bookId)
        viewModel.navigate(direction)
    }
}