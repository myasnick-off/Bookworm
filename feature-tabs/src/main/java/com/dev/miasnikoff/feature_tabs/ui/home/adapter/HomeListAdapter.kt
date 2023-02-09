package com.dev.miasnikoff.feature_tabs.ui.home.adapter

import com.dev.miasnikoff.core_ui.adapter.BaseListAdapter
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.bookofday.BookOfDayCell
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.carousel.CarouselWithTitleCell

class HomeListAdapter(
    itemClickListener: BookOfDayCell.ItemClickListener,
    carouselClickListener: CarouselWithTitleCell.ItemClickListener,
) : BaseListAdapter(
    cells = arrayOf(
        BookOfDayCell(itemClickListener),
        CarouselWithTitleCell(carouselClickListener)
    )
)