package com.bobs.mapque.searchlist.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@Entity(tableName = "search_items")
data class SearchItem(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "search_query") val searchQuery: String? = "",
    @ColumnInfo(name = "search_address_name") val searchAddressName: String? = "",
    @ColumnInfo(name = "search_latitude") val searchLatitude: Double,
    @ColumnInfo(name = "search_longitude") val searchLongitude: Double,
    @ColumnInfo(name = "search_date") val searchDate: DateTime? = DateTime()
) {
    fun getFormatSearchDate() : String? {
        // 검색 시간을 리스트 아이템에서 보여줄 경우 아래 포멧으로 변경한다
        val fmt = DateTimeFormat.forPattern("yyyy년 MM월 dd일 HH시 mm분")
        return searchDate?.toString(fmt)
    }
}
