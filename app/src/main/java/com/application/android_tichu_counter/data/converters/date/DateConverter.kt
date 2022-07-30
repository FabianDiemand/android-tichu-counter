package com.application.android_tichu_counter.data.converters.date

import androidx.room.TypeConverter
import com.application.android_tichu_counter.domain.date.DateUtils

class DateConverter {
    @TypeConverter
    fun fromTimestamp(timeStamp: Long?): String? {
        return DateUtils.formatFromTimeStamp(timeStamp)
    }

    @TypeConverter
    fun dateToTimestamp(timeStamp: String?): Long? {
        return DateUtils.formatDatestringToTimestamp(timeStamp)
    }
}