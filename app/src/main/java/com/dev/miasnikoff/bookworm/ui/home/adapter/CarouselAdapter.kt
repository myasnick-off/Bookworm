package com.dev.miasnikoff.bookworm.ui.home.adapter

import com.dev.miasnikoff.bookworm.ui._core.adapter.BaseListAdapter
import com.dev.miasnikoff.bookworm.ui.home.adapter.genre.GenreCell
import com.dev.miasnikoff.bookworm.ui.home.adapter.litebook.LiteBookCell

class CarouselAdapter(
    genreClickListener: GenreCell.ItemClickListener,
    bookClickListener: LiteBookCell.ItemClickListener,
) : BaseListAdapter(GenreCell(genreClickListener), LiteBookCell(bookClickListener))