package com.dev.miasnikoff.feature_tabs.ui.base

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dev.miasnikoff.core_ui.BaseFragment
import com.dev.miasnikoff.core_ui.R
import com.dev.miasnikoff.core_ui.adapter.RecyclerItem
import com.dev.miasnikoff.core_ui.extensions.showSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseListFragment(layoutRes: Int): BaseFragment(layoutRes) {

    abstract override val viewModel: BaseListViewModel

    private val contentRecycler: RecyclerView?
        get() = view?.findViewById(R.id.content_recycler)

    private val loaderView: ProgressBar?
        get() = view?.findViewById(R.id.loader)

    private val emptyView: LinearLayout?
        get() = view?.findViewById(R.id.empty_result)

    private val errorView: ImageView?
        get() = view?.findViewById(R.id.error)

    private val refreshLayout: SwipeRefreshLayout?
        get() = view?.findViewById(R.id.refresh)

    override fun initView() {
        super.initView()
        refreshLayout?.setOnRefreshListener {
            refreshLayout?.isRefreshing = false
            viewModel.getInitialData()
        }
    }

    open fun initViewModel() {
        viewModel.screenState.onEach(::renderState).launchIn(lifecycleScope)
        viewModel.screenEvent.onEach(::renderEvent).launchIn(lifecycleScope)
    }

    abstract fun updateList(data: List<RecyclerItem>, loadMore: Boolean)

    private fun renderState(state: ListState) {
        when (state) {
            is ListState.Empty -> showEmpty()
            is ListState.EmptyLoading -> showEmptyLoading()
            is ListState.Loading -> showLoading()
            is ListState.Failure -> showError()
            is ListState.Success -> showData(state.data, state.loadMore)
        }
    }

    private fun renderEvent(event: ScreenEvent) {
        when(event) {
            is ScreenEvent.Error -> showErrorMessage()
        }
    }

    private fun showEmpty() {
        loaderView?.visibility = View.GONE
        errorView?.visibility = View.GONE
        contentRecycler?.visibility = View.GONE
        emptyView?.visibility = View.VISIBLE
    }

    private fun showEmptyLoading() {
        loaderView?.visibility = View.VISIBLE
        errorView?.visibility = View.GONE
        contentRecycler?.visibility = View.GONE
        emptyView?.visibility = View.GONE
    }

    private fun showLoading() {
        loaderView?.visibility = View.VISIBLE
        contentRecycler?.visibility = View.VISIBLE
        errorView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
    }

    private fun showData(data: List<RecyclerItem>, loadMore: Boolean) {
        loaderView?.visibility = View.GONE
        errorView?.visibility = View.GONE
        emptyView?.visibility = View.GONE
        contentRecycler?.visibility = View.VISIBLE
        updateList(data, loadMore)
    }

    private fun showError() {
        loaderView?.visibility = View.GONE
        contentRecycler?.visibility = View.GONE
        emptyView?.visibility = View.GONE
        errorView?.visibility = View.VISIBLE
        binding.root.showSnackBar(
            message = getString(R.string.message_error),
            actionText = getString(R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { viewModel.getInitialData() }
    }

    private fun showErrorMessage() {
        binding.root.showSnackBar(
            message = getString(R.string.message_error),
            actionText = getString(R.string.reload),
            length = Snackbar.LENGTH_LONG,
        ) { viewModel.getInitialData() }
    }
}