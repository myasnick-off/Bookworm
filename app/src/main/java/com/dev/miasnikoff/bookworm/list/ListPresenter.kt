package com.dev.miasnikoff.bookworm.list

interface ListPresenter {
    fun attachView(view: ListView)
    fun detachView()
    fun getVolumeList(query: String)
}