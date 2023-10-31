package com.surodeevartem.imageviewer.utils

import kotlinx.collections.immutable.PersistentList

fun <T> PersistentList<T>.update(
    condition: (T) -> Boolean,
    transform: (T) -> T,
): PersistentList<T> {
    val index = this.indexOfFirst(condition)
    return if (index != -1) {
        this.set(index, transform(this[index]))
    } else {
        this
    }
}
