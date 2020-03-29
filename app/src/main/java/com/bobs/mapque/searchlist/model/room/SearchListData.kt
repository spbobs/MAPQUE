package com.bobs.mapque.searchlist.model.room

interface SearchListData {
    fun getAllSearchList() : List<SearchItem>

    fun insert(searchItem: SearchItem)

    fun delete(searchItem: SearchItem)

    // 현재 버전에선 사용 안함
    fun deleteAll()
}