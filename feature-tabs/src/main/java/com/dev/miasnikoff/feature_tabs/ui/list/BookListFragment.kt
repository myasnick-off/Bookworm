package com.dev.miasnikoff.feature_tabs.ui.list

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
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.adapter.BasePagedListAdapter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.showSnackBar
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentListBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookCell
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookListAdapter
import com.dev.miasnikoff.feature_tabs.ui.list.model.PagedListState
import com.dev.miasnikoff.feature_tabs.ui.search.SearchClickListener
import com.dev.miasnikoff.feature_tabs.ui.search.SearchDialogFragment
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class BookListFragment : BaseFragment(R.layout.fragment_list), MenuProvider {

    override lateinit var binding: FragmentListBinding

    private val args: BookListFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: BookListViewModelAssistedFactory
    private val viewModel: BookListViewModel by viewModels {
        viewModelFactory.create(args.query, args.category)
    }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            navigateToDetails(itemId)
        }

        override fun onItemLongClick(itemId: String) {
            //todo: change toast to real action
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

    private val bookListAdapter = BookListAdapter(pageListener, itemClickListener)

    private var fabAnimSet: AnimatorSet? = null

    override fun onAttach(context: Context) {
        viewModel<TabsFeatureComponentViewModel>().component.inject(this)
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
        (requireActivity() as AppCompatActivity).apply {
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
                    viewModel.getData(query = phrase)
                }

                override fun onDialogDismiss() {
                    fabAnimSet?.start()
                }
            })
        }.show(parentFragmentManager, null)
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
            message = "${getString(com.dev.miasnikoff.core_ui.R.string.error)} $message",
            actionText = getString(com.dev.miasnikoff.core_ui.R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { viewModel.getData() }
    }

    private fun navigateToDetails(bookId: String) {
        val direction =
            BookListFragmentDirections.actionBookListFragmentToBookDetailsFragment(bookId)
        viewModel.navigate(direction)
    }

    companion object {
        private const val FAB_ANIMATION_DURATION = 500L
    }
}