package io.github.devtronaut.android_tichu_counter.domain.date


import io.github.devtronaut.android_tichu_counter.domain.locale.LocaleUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class to format dates regarding the locale.
 *
 * Copyright (C) 2022 Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * Find a copy of the GNU AGPLv3 in the root-level file "LICENCE".
 */
object DateUtils {
    private val simpleFormatter: DateFormat = SimpleDateFormat.getDateInstance(
        SimpleDateFormat.MEDIUM,
        Locale.forLanguageTag(LocaleUtils.getDefaultLocale())
    )

    fun formatDateToLocale(dateTime: Date): String {
        return simpleFormatter.format(dateTime)
    }
}