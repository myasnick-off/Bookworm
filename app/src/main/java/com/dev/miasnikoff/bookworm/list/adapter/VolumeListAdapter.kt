package com.dev.miasnikoff.bookworm.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dev.miasnikoff.bookworm.core.ui.adapter.RecyclerItem
import com.dev.miasnikoff.bookworm.core.ui.adapter.diffUtilItemCallback
import com.dev.miasnikoff.bookworm.databinding.ItemVolumeListBinding

class VolumeListAdapter : ListAdapter<RecyclerItem, VolumeViewHolder>(diffUtilItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVolumeListBinding.inflate(inflater, parent, false)
        return VolumeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VolumeViewHolder, position: Int) {
        val item = getItem(position)
        if (item is VolumeItem) {
            holder.bind(item)
        }
    }
}