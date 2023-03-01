package com.dev.miasnikoff.core_ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagedListAdapter(
    diffUtilItemCallback: DiffUtil.ItemCallback<RecyclerItem> = BaseDiffUtilItemCallback(),
    private val pageListener: PageListener?,
    vararg cells: Cell<RecyclerItem>
) : BaseListAdapter(diffUtilItemCallback, *cells) {

    private var loadMore: Boolean = false

    fun updateList(volumeList: List<RecyclerItem>, isLoadMore: Boolean) {
        submitList(volumeList)
        loadMore = isLoadMore
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (loadMore && (position + ITEMS_LEFT) >= itemCount) {
            loadMore = false
            pageListener?.loadNextPage()
        }
    }

    companion object {
        private const val ITEMS_LEFT = 4
    }

    interface PageListener {
        fun loadNextPage()
    }
}