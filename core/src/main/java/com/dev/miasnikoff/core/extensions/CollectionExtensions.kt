package com.dev.miasnikoff.core.extensions

fun <T> MutableList<T>.addNotNull(item: T?) {
    item?.let { this.add(it) }
}