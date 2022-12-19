package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.core.network.model.Volume

interface ListView {
    fun showList(volumes: List<Volume>)
    fun showError(message: String)

}