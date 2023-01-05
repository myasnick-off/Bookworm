package com.dev.miasnikoff.bookworm.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation._core.adapter.diffUtilItemCallback
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem

class VolumeListAdapter(
    private val itemClickListener: ItemClickListener,
    private val pageListener: PageListener
) :
    ListAdapter<RecyclerItem, VolumeViewHolder>(diffUtilItemCallback) {

    private var loadMore: Boolean = false

    fun updateList(volumeList: List<RecyclerItem>, isLoadMore: Boolean) {
        submitList(volumeList)
        loadMore = isLoadMore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVolumeListBinding.inflate(inflater, parent, false)
        return VolumeViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        if (loadMore && (position + ITEMS_LEFT) >= itemCount) {
            loadMore = false
            pageListener.loadNextPage()
        }
        (getItem(position) as? VolumeItem)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(
        holder: VolumeViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads.any { it !is Int }) {
            onBindViewHolder(holder, position)
        } else {
            val iconRes = payloads.first { it is Int } as Int
            holder.updateFavorite(iconRes)
        }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
        fun onItemLongClick(itemId: String): Boolean
        fun onFavoriteClick(itemId: String)
    }

    interface PageListener {
        fun loadNextPage()
    }


    companion object {
        private const val ITEMS_LEFT = 2
    }
}