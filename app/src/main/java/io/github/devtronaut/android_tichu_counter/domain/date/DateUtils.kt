package io.github.devtronaut.android_tichu_counter.domain.date


import io.github.devtronaut.android_tichu_counter.domain.locale.LocaleUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val simpleFormatter: DateFormat = SimpleDateFormat.getDateInstance(
        SimpleDateFormat.MEDIUM,
        Locale.forLanguageTag(LocaleUtils.getDefaultLocale())
    )

    fun formatDateToLocale(dateTime: Date): String {
        return simpleFormatter.format(dateTime)
    }
}