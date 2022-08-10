package io.github.devtronaut.android_tichu_counter.data.converters.date

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): Date? {
        return timeStamp?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun dateToTimestamp(timeStamp: Date?): Long? {
        return timeStamp?.time
    }
}