package com.dev.miasnikoff.feature_tabs.ui.home.adapter

import com.dev.miasnikoff.core_ui.adapter.BaseListAdapter
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.genre.GenreCell
import com.dev.miasnikoff.feature_tabs.ui.home.adapter.litebook.LiteBookCell

class CarouselAdapter(
    genreClickListener: GenreCell.ItemClickListener,
    bookClickListener: LiteBookCell.ItemClickListener,
) : BaseListAdapter(cells = arrayOf(GenreCell(genreClickListener), LiteBookCell(bookClickListener)))