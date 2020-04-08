package com.bobs.mapque.searchlist.data.source

import com.bobs.mapque.searchlist.data.model.SearchItem

interface SearchListDataSource {
    fun getAllSearchList() : List<SearchItem>

    fun insert(searchItem: SearchItem)

    fun delete(searchItem: SearchItem)

    // 현재 버전에선 사용 안함
    fun deleteAll()
}