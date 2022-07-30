package com.application.android_tichu_counter.domain.locale

import android.content.Context
import com.application.android_tichu_counter.domain.preferences.PreferenceUtils
import java.util.*

/**
 * Object with utility functions and constants for dealing with in-app language changes.
 * Supported languages are swiss-german, german and english.
 *
 * @author Devtronaut
 *
 * @property LANG_SWISS_GERMAN locale code for swiss-german dialect
 * @property LANG_GERMAN locale code for german language
 * @property LANG_ENGLISH locale code for english language
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