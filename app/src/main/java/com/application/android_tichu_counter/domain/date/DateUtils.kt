package com.application.android_tichu_counter.domain.date


import com.application.android_tichu_counter.domain.locale.LocaleUtils
import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*

object DateUtils {
    private val formatter: DateFormat = DateFormat.getDateInstance(
        DateFormat.MEDIUM,
        Locale.forLanguageTag(LocaleUtils.getDefaultLocale())
    )

    fun formatDateToLocale(dateTime: LocalDateTime): String? {
        return formatter.format(dateTime)
    }

    fun formatFromTimeStamp(timeStamp: Long?): String? {
        return timeStamp?.let {
            formatter.format(timeStamp)
        }
    }

    fun formatDatestringToTimestamp(timeStamp: String?): Long? {
        return timeStamp?.let {
            formatter.parse(it)?.time
        }
    }
}