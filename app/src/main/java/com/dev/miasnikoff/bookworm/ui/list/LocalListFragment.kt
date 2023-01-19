package com.dev.miasnikoff.bookworm.ui.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentListBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.adapter.BasePagedListAdapter
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.details.VolumeDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookCell
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookListAdapter
import com.dev.miasnikoff.bookworm.ui.list.model.PagedListState
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar

class LocalListFragment : BaseFragment(), MenuProvider {

    private lateinit var _binding: FragmentListBinding
    override val binding: FragmentListBinding
        get() = _binding

    private val viewModel: LocalListViewModel by lazy {
        ViewModelProvider(this)[LocalListViewModel::class.java]
    }

    private val category: Category? by lazy { arguments?.getParcelable(ARG_CATEGORY) }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            openFragment(fragment = VolumeDetailsFragment.newInstance(itemId))
        }

        override fun onItemLongClick(itemId: String): Boolean {
            Toast.makeText(context, "Made long click on item with id #$itemId", Toast.LENGTH_SHORT)
                .show()
            return true
        }

        override fun onFavoriteClick(itemId: String) {
            viewModel.setFavorite(itemId)
        }
    }

    private val pageListener = object : BasePagedListAdapter.PageListener {
        override fun loadNextPage() {}
    }

    private val bookListAdapter: BookListAdapter = BookListAdapter(pageListener, itemClickListener)

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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_list_local, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            R.id.menu_remove_all -> {
                if (category == Category.LAST_VIEWED) {
                    showAlertDialog(R.string.remove_history_warning) { viewModel.removeHistory() }
                }
                if (category == Category.FAVORITE) {
                    showAlertDialog(R.string.remove_favorites_warning) { viewModel.removeFavorites() }
                }
                true
            }
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> false
        }
    }

    override fun initView() {
        binding.volumeList.adapter = bookListAdapter
        binding.listFab?.visibility = View.GONE
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
        getData()
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
        binding.listLoader?.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
        binding.volumeList.visibility = View.GONE
    }

    private fun showMoreLoading() {
        binding.listLoader?.visibility = View.VISIBLE
        binding.volumeList.visibility = View.VISIBLE
        binding.errorImage.visibility = View.GONE
    }

    private fun showData(volumes: List<RecyclerItem>, loadMore: Boolean) {
        binding.listLoader?.visibility = View.GONE
        binding.errorImage.visibility = View.GONE
        binding.volumeList.visibility = View.VISIBLE
        bookListAdapter.updateList(volumes, loadMore)
    }

    private fun showError(message: String) {
        binding.listLoader?.visibility = View.GONE
        binding.volumeList.visibility = View.GONE
        binding.errorImage.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = "${getString(R.string.error)} $message",
            actionText = getString(R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { getData() }
    }

    private fun getData() {
        category?.let { viewModel.getInitialPage(category = it) }
    }

    companion object {
        private const val ARG_CATEGORY = "arg_category"

        fun newInstance(category: Category): LocalListFragment =
            LocalListFragment().apply {
                arguments = bundleOf(ARG_CATEGORY to category)
            }
    }
}