package com.dev.miasnikoff.bookworm.ui.list

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.dev.miasnikoff.bookworm.App
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.databinding.FragmentListBinding
import com.dev.miasnikoff.bookworm.ui._core.BaseFragment
import com.dev.miasnikoff.bookworm.ui._core.ViewModelFactory
import com.dev.miasnikoff.bookworm.ui._core.adapter.BasePagedListAdapter
import com.dev.miasnikoff.bookworm.ui._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.ui.details.BookDetailsFragment
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.Category
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookCell
import com.dev.miasnikoff.bookworm.ui.list.adapter.BookListAdapter
import com.dev.miasnikoff.bookworm.ui.list.model.PagedListState
import com.dev.miasnikoff.bookworm.ui.main.MainActivity
import com.dev.miasnikoff.bookworm.ui.search.SearchClickListener
import com.dev.miasnikoff.bookworm.ui.search.SearchDialogFragment
import com.dev.miasnikoff.bookworm.utils.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class BookListFragment : BaseFragment(R.layout.fragment_list), MenuProvider {

    override lateinit var binding: FragmentListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: BookListViewModel by viewModels { viewModelFactory }

    private val query: String? by lazy { arguments?.getString(ARG_QUERY, null) }
    private val category: Category? by lazy { arguments?.getParcelable(ARG_CATEGORY) }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            openFragment(fragment = BookDetailsFragment.newInstance(itemId))
        }

        override fun onItemLongClick(itemId: String) {
            Toast.makeText(context, "Made long click on item with id #$itemId", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onFavoriteClick(itemId: String) {
            viewModel.setFavorite(itemId)
        }
    }

    private val pageListener = object : BasePagedListAdapter.PageListener {
        override fun loadNextPage() {
            viewModel.loadNextPage()
        }
    }

    private val bookListAdapter: BookListAdapter =
        BookListAdapter(pageListener, itemClickListener)

    private var fabAnimSet: AnimatorSet? = null

    override fun onAttach(context: Context) {
        App.appInstance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        initView()
        initMenu()
        initPresenter()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> false
        }
    }


    override fun initView() {
        binding.volumeList.adapter = bookListAdapter
        val animScaleX = ObjectAnimator.ofFloat(binding.listFab, View.SCALE_X, 0f, 1f).apply {
            duration = FAB_ANIMATION_DURATION
            start()
        }
        val animScaleY = ObjectAnimator.ofFloat(binding.listFab, View.SCALE_Y, 0f, 1f).apply {
            duration = FAB_ANIMATION_DURATION
            start()
        }
        val animAlpha = ObjectAnimator.ofFloat(binding.listFab, View.ALPHA, 0f, 1f).apply {
            duration = FAB_ANIMATION_DURATION
            start()
        }
        fabAnimSet = AnimatorSet().apply {
            playTogether(animScaleX, animScaleY, animAlpha)
            start()
        }
        binding.listFab.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fabAnimSet?.reverse()
            }
            showSearchDialog()
        }
    }

    override fun initMenu() {
        (requireActivity() as MainActivity).apply {
            setSupportActionBar(binding.listToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showSearchDialog() {
        SearchDialogFragment.newInstance().apply {
            setOnSearchClickListener(object : SearchClickListener {
                override fun onSearchClick(phrase: String) {
                    viewModel.getInitialPage(query = phrase)
                }

                override fun onDialogDismiss() {
                    fabAnimSet?.start()
                }
            })
        }.show(parentFragmentManager, null)
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
        query?.let { viewModel.getInitialPage(query = it) }
        category?.let { viewModel.getInitialPage(category = it) }

    }

    companion object {
        private const val FAB_ANIMATION_DURATION = 500L
        private const val ARG_QUERY = "arg_query"
        private const val ARG_CATEGORY = "arg_category"

        fun newInstance(query: String? = null, category: Category? = null): BookListFragment =
            BookListFragment().apply {
                arguments = bundleOf(ARG_QUERY to query, ARG_CATEGORY to category)
            }
    }
}