package com.dev.miasnikoff.core.extensions

fun <T> MutableList<T>.addNotNull(item: T?) {
    item?.let { this.add(it) }
}

fun <T> MutableList<T>.addNotNull(index: Int, item: T?) {
    item?.let { this.add(index, it) }
}