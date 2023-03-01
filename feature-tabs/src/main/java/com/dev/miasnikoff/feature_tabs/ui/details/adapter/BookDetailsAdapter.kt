package com.dev.miasnikoff.feature_tabs.ui.details.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dev.miasnikoff.core_ui.adapter.BaseListAdapter
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsCell
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsHolder
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.main.DetailsMainCell
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithheader.TextWithHeaderCell
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithlabel.TextWithLabelCell

class BookDetailsAdapter(controlsClickListener: BookControlsCell.ControlsClickListener) :
    BaseListAdapter(
        diffUtilItemCallback = DetailsDiffUtilItemCallback(),
        cells = arrayOf(
            DetailsMainCell(),
            TextWithLabelCell(),
            TextWithHeaderCell(),
            BookControlsCell(controlsClickListener)
        )
    ) {
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads.any { it !is Int }) {
            super.onBindViewHolder(holder, position)
        } else {
            val iconRes = payloads.first { it is Int } as Int
            (holder as? BookControlsHolder)?.updateFavoriteButton(iconRes)
        }
    }
}