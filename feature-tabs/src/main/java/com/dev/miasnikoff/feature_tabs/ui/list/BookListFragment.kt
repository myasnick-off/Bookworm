package com.dev.miasnikoff.feature_tabs.ui.list

import android.animation.AnimatorSet
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.adapter.BasePagedListAdapter
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.createObjectAnimator
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentListBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import com.dev.miasnikoff.feature_tabs.ui.base.BaseListFragment
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookCell
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookListAdapter
import com.dev.miasnikoff.feature_tabs.ui.search.SearchClickListener
import com.dev.miasnikoff.feature_tabs.ui.search.SearchDialogFragment
import javax.inject.Inject

class BookListFragment : BaseListFragment(R.layout.fragment_list) {

    override lateinit var binding: FragmentListBinding
    private val args: BookListFragmentArgs by navArgs()

    override val titleRes: Int?
        get() = if (args.query.isNullOrEmpty()) args.category.titleRes else null

    @Inject
    lateinit var viewModelFactory: BookListViewModelAssistedFactory
    override val viewModel: BookListViewModel by viewModels {
        viewModelFactory.create(args.query, args.category)
    }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            navigateToDetails(itemId)
        }

        override fun onItemLongClick(itemId: String) {
            viewModel.setFavorite(itemId)
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
        initViewModel()
    }

    override fun initView() {
        super.initView()
        binding.contentRecycler.adapter = bookListAdapter
        binding.contentRecycler.itemAnimator = null
        initFab()
    }


    private fun initFab() = with(binding) {
        val animScaleX = listFab.createObjectAnimator(View.SCALE_X, FAB_ANIMATION_DURATION)
        val animScaleY = listFab.createObjectAnimator(View.SCALE_Y, FAB_ANIMATION_DURATION)
        val animAlpha = listFab.createObjectAnimator(View.ALPHA, FAB_ANIMATION_DURATION)
        fabAnimSet = AnimatorSet().apply {
            playTogether(animScaleX, animScaleY, animAlpha)
            start()
        }
        listFab.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fabAnimSet?.reverse()
            }
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {
        SearchDialogFragment.newInstance().apply {
            setOnSearchClickListener(object : SearchClickListener {
                override fun onSearchClick(phrase: String) {
                    viewModel.getDataByQuery(query = phrase)
                }

                override fun onDialogDismiss() {
                    fabAnimSet?.start()
                }
            })
        }.show(parentFragmentManager, null)
    }

     override fun updateList(data: List<RecyclerItem>, loadMore: Boolean) {
        bookListAdapter.updateList(data, loadMore)
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