package com.dev.miasnikoff.bookworm.list.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.core.ui.adapter.diffUtilItemCallback
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding

class VolumeListAdapter(
    private val itemClickListener: ItemClickListener,
    private val pageListener: PageListener
) :
    ListAdapter<RecyclerItem, VolumeViewHolder>(diffUtilItemCallback) {

    var loadMore: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVolumeListBinding.inflate(inflater, parent, false)
        return VolumeViewHolder(binding, itemClickListener)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        if (loadMore && (position + ITEMS_LEFT) >= itemCount) {
            loadMore = false
            pageListener.loadNextPage()
        }
        (getItem(position) as? VolumeItem)?.let { holder.bind(it) }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
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