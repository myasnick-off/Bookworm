package com.dev.miasnikoff.feature_tabs.ui.details.adapter

import com.dev.miasnikoff.core_ui.adapter.BaseListAdapter
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.controls.BookControlsCell
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.main.DetailsMainCell
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithheader.TextWithHeaderCell
import com.dev.miasnikoff.feature_tabs.ui.details.adapter.textwithlabel.TextWithLabelCell

class BookDetailsAdapter(controlsClickListener: BookControlsCell.ControlsClickListener) :
    BaseListAdapter(
        cells = arrayOf(
            DetailsMainCell(),
            TextWithLabelCell(),
            TextWithHeaderCell(),
            BookControlsCell(controlsClickListener)
        )
    )