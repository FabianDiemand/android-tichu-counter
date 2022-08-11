package io.github.devtronaut.android_tichu_counter.domain.locale

import android.content.Context
import io.github.devtronaut.android_tichu_counter.domain.preferences.PreferenceUtils
import java.util.*

/**
 * Object with utility functions and constants for dealing with in-app language changes.
 * Supported languages are swiss-german, german and english.
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
object LocaleUtils {
    const val LANG_SWISS_GERMAN = "gsw"
    const val LANG_GERMAN = "de"
    const val LANG_ENGLISH = "en"

    /**
     * Extension of the Context object to change the applications locale according to the passed string.
     *
     * @param language locale code for the desired language. Use LocaleUtils properties LANG_SWISS_GERMAN, LANG_GERMAN or LANG_ENGLISH.
     */
    fun Context.setAppLocale(language: String): Context{
        val locale = Locale(language)
        Locale.setDefault(Locale(language))

        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return createConfigurationContext(config)
    }

    /**
     * Persist the new default language.
     *
     * @param language locale code for the desired language. Use LocaleUtils properties LANG_SWISS_GERMAN, LANG_GERMAN or LANG_ENGLISH.
     */
    fun persistDefaultLocale(language: String){
        PreferenceUtils.persistLanguage(language)
    }

    /**
     * Get the default languages from app preferences.
     *
     * @return locale code of the default language (fallback is english (en))
     */
    fun getDefaultLocale(): String {
        return PreferenceUtils.getPrefLanguage(LANG_ENGLISH)!!
    }
}