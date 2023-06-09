package com.dev.miasnikoff.core_ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter(
    diffUtilItemCallback: DiffUtil.ItemCallback<RecyclerItem> = BaseDiffUtilItemCallback(),
    vararg cells: Cell<RecyclerItem>
) :
    ListAdapter<RecyclerItem, RecyclerView.ViewHolder>(diffUtilItemCallback) {

    private val cellTypes: CellTypes<RecyclerItem> = CellTypes(*cells)

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return cellTypes.of(item).type()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return cellTypes.of(viewType).holder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        cellTypes.of(item).bind(holder, item)
    }
}