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

    fun markAsFavorite(itemId: String) {
        val newList: MutableList<RecyclerItem> = mutableListOf()
        newList.addAll(currentList)
        val index = newList.indexOfFirst { it.id == itemId }
        val volumeItem = (newList.firstOrNull { it.id == itemId } as? VolumeItem)
        if (index > -1 && volumeItem != null) {
            newList[index] = if (volumeItem.isFavorite)
                volumeItem.copy(isFavorite = false, favoriteIcon = R.drawable.ic_bookmark_border_24)
            else
                volumeItem.copy(isFavorite = true, favoriteIcon = R.drawable.ic_bookmark_24)
        }
        submitList(newList)
    }

    interface ItemClickListener {
        fun onItemClick()
        fun onItemLongClick(): Boolean
        fun onIconClick(itemId: String)
    }

    companion object {
        private const val VOLUME_ITEM_TYPE = 1
    }
}