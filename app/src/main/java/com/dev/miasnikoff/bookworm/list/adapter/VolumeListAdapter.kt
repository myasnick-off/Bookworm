package com.dev.miasnikoff.bookworm.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.R
import com.dev.miasnikoff.bookworm.core.ui.adapter.BaseViewHolder
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.core.ui.adapter.diffUtilItemCallback
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding

class VolumeListAdapter(private val itemClickListener: ItemClickListener) :
    ListAdapter<RecyclerItem, BaseViewHolder>(diffUtilItemCallback) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is VolumeItem) VOLUME_ITEM_TYPE
        else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ItemVolumeListBinding.inflate(inflater, parent, false)
        return VolumeViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads.any { it !is Int }) {
            onBindViewHolder(holder, position)
        } else {
            val iconRes = payloads.first { it is Int } as Int
            (holder as? VolumeViewHolder)?.updateFavorite(iconRes)
        }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
        fun onItemLongClick(itemId: String): Boolean
        fun onIconClick(itemId: String)
    }

    companion object {
        private const val VOLUME_ITEM_TYPE = 1
    }
}