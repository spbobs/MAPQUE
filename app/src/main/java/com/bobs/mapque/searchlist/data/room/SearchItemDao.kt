package com.bobs.mapque.searchlist.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bobs.mapque.searchlist.data.model.SearchItem

@Dao
interface SearchItemDao {

    @Query("SELECT * FROM search_items order by search_date desc")
    fun getAllSearchItems() : List<SearchItem>

    @Insert
    fun insertSearchItem(searchItem: SearchItem)

    @Delete
    fun deleteSearchItem(searchItem: SearchItem)

    // 현재 버전에선 사용 안함
    @Query("DELETE FROM search_items")
    fun deleteAll()
}