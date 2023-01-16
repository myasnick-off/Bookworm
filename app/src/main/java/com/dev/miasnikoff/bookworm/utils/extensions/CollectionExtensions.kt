package com.dev.miasnikoff.bookworm.utils.extensions

fun <T> MutableList<T>.addNotNull(item: T?) {
    item?.let { this.add(it) }
}