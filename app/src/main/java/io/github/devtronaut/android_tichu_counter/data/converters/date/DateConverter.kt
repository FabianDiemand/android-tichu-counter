package io.github.devtronaut.android_tichu_counter.data.converters.date

import androidx.room.TypeConverter
import java.util.*

/**
 * Converter class for dates (Date to Long respectively).
 *
 * Copyright (C) 2022  Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Find a copy of the GNU GPL in the root-level file "LICENCE".
 */
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