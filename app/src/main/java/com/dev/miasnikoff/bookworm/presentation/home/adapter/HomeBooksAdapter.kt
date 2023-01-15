package com.dev.miasnikoff.bookworm.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.databinding.ItemHomeListBinding
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding
import com.dev.miasnikoff.bookworm.presentation._core.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.presentation._core.adapter.diffUtilItemCallback
import com.dev.miasnikoff.bookworm.presentation.home.model.HomeBookItem
import com.dev.miasnikoff.bookworm.presentation.list.model.VolumeItem

class HomeBooksAdapter(
    private val itemClickListener: ItemClickListener,
) :
    ListAdapter<RecyclerItem, HomeBookViewHolder>(diffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeListBinding.inflate(inflater, parent, false)
        return HomeBookViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: HomeBookViewHolder, position: Int) {
        (getItem(position) as? HomeBookItem)?.let { holder.bind(it) }
    }

    interface ItemClickListener {
        fun onItemClick(itemId: String)
    }
}