package com.dev.miasnikoff.bookworm.ui.list

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentListLocalBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.adapter.BasePagedListAdapter
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookCell
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookListAdapter
import com.dev.miasnikoff.bookworm.ui.list.model.PagedListState
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class LocalListFragment : BaseFragment(R.layout.fragment_list_local), MenuProvider {

    override lateinit var binding: FragmentListLocalBinding

    private val args: LocalListFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: LocalListViewModelAssistedFactory
    private val viewModel: LocalListViewModel by viewModels {
        viewModelFactory.create(Category.valueOf(args.categoryName))
    }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            navigateToDetails(itemId)
        }

        override fun onItemLongClick(itemId: String) {
            viewModel.removeFromLocal(itemId)
        }

        override fun onFavoriteClick(itemId: String) {
            viewModel.setFavorite(itemId)
        }
    }

    private val pageListener = object : BasePagedListAdapter.PageListener {
        override fun loadNextPage() {}
    }

    private val bookListAdapter: BookListAdapter = BookListAdapter(pageListener, itemClickListener)

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListLocalBinding.bind(view)
        initView()
        initMenu()
        initPresenter()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_local, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_remove_all -> {
                if (args.categoryName == Category.LAST_VIEWED.name) {
                    showAlertDialog(R.string.remove_history_warning) { viewModel.removeHistory() }
                }
                if (args.categoryName == Category.FAVORITE.name) {
                    showAlertDialog(R.string.remove_favorites_warning) { viewModel.removeFavorites() }
                }
                true
            }
            android.R.id.home -> {
                viewModel.back()
                true
            }
            else -> false
        }
    }

    override fun initView() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            viewModel.back()
        }
        binding.volumeList.adapter = bookListAdapter
        binding.listFab.visibility = View.GONE
    }

    override fun initMenu() {
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.listToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initPresenter() {
        viewModel.liveData.observe(viewLifecycleOwner, ::renderState)
    }

    private fun renderState(state: PagedListState) {
        when (state) {
            is PagedListState.Loading -> showLoading()
            is PagedListState.MoreLoading -> showMoreLoading()
            is PagedListState.Failure -> showError(state.message)
            is PagedListState.Success -> showData(state.data, state.loadMore)
        }
    }

    private fun showLoading() {
        binding.listLoader.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.volumeList.visibility = View.GONE
    }

    private fun showMoreLoading() {
        binding.listLoader.visibility = View.VISIBLE
        binding.volumeList.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
    }

    private fun showData(volumes: List<RecyclerItem>, loadMore: Boolean) {
        binding.listLoader.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.volumeList.visibility = View.VISIBLE
        bookListAdapter.updateList(volumes, loadMore)
    }

    private fun showError(message: String) {
        binding.listLoader.visibility = View.GONE
        binding.volumeList.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { getData() }
    }

    private fun getData() {
        viewModel.getInitialPage()
    }

    private fun navigateToDetails(bookId: String) {
        val direction =
            LocalListFragmentDirections.actionLocalListFragmentToBookDetailsFragment(bookId)
        viewModel.navigate(direction)
    }
}