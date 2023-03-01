package com.dev.miasnikoff.feature_tabs.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dev.miasnikoff.core_di.ViewModelFactory
import com.dev.miasnikoff.core_navigation.viewModel
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.showAlertDialog
import com.dev.miasnikoff.feature_tabs.R
import com.dev.miasnikoff.feature_tabs.databinding.FragmentListBinding
import com.dev.miasnikoff.feature_tabs.di.TabsFeatureComponentViewModel
import com.dev.miasnikoff.feature_tabs.ui.base.BaseListFragment
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookCell
import com.dev.miasnikoff.feature_tabs.ui.list.adapter.BookListAdapter
import javax.inject.Inject

class HistoryListFragment : BaseListFragment(R.layout.fragment_list) {

    override lateinit var binding: FragmentListBinding
    override val titleRes = R.string.view_history
    override val hasRemoveAllButton = true
    override val removeAllAction: () -> Unit
        get() = {
            showAlertDialog(com.dev.miasnikoff.core_ui.R.string.remove_history_warning) { viewModel.removeAllHistory() }
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override val viewModel: HistoryViewModel by viewModels { viewModelFactory }

    private val itemClickListener = object : BookCell.ItemClickListener {
        override fun onItemClick(itemId: String) {
            navigateToDetails(itemId)
        }

        override fun onItemLongClick(itemId: String) {
            viewModel.removeFromHistory(itemId)
        }

        override fun onFavoriteClick(itemId: String) {
            viewModel.setFavorite(itemId)
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

    override fun updateList(data: List<RecyclerItem>, loadMore: Boolean) {
        bookListAdapter.updateList(data, loadMore)
    }

    private fun navigateToDetails(bookId: String) {
        val direction =
            HistoryListFragmentDirections.actionHistoryListFragmentToBookDetailsFragment(bookId)
        viewModel.navigate(direction)
    }
}