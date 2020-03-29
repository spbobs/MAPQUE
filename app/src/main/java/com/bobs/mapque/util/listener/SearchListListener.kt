package com.bobs.mapque.util.listener

interface SearchListListener<T> {
    fun shareData(item: T)
    fun deleteItem(item: T)
}