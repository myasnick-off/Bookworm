package com.dev.miasnikoff.bookworm.ui.home.adapter

import com.dev.miasnikoff.bookworm.ui._core.adapter.BaseListAdapter
import com.dev.miasnikoff.bookworm.ui.home.adapter.bookofday.BookOfDayCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.carousel.CarouselWithTitleCell

class HomeListAdapter(
    itemClickListener: BookOfDayCell.ItemClickListener,
    carouselClickListener: CarouselWithTitleCell.ItemClickListener,
) : BaseListAdapter(BookOfDayCell(itemClickListener), CarouselWithTitleCell(carouselClickListener))