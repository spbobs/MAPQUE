package com.bobs.mapque.searchlist.data.room

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateConverter {
    @TypeConverter
    fun longToDateTime(time: Long?)= DateTime(time)

    @TypeConverter
    fun datetimeToLong(datetime: DateTime) = datetime.millis
}