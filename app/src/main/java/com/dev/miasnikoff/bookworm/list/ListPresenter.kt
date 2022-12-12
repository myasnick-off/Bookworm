package com.dev.miasnikoff.bookworm.list

import com.dev.miasnikoff.bookworm.core.network.model.Volume

interface ListPresenter {
    fun attachView(view: ListView)
    fun detachView()
    fun getVolumeList(query: String)
}