package com.application.android_tichu_counter.domain.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.application.android_tichu_counter.TichuApplication

/**
 * Utility object to centralize access to the apps preferences.
 *
 * @author Devtronaut
 */
object PreferenceUtils{
    // Shared Preference Keys
    private const val APP_LANGUAGE_KEY = "Application_Default_Language"
    private const val APP_SCREEN_MODE_KEY = "Application_Default_Screen_Mode"

    /**
     * Persists the default language in the preferences.
     *
     * @param language the language (locale code) to store in the preferences.
     */
    fun persistLanguage(language: String) {
        getEditor().putString(APP_LANGUAGE_KEY, language).apply()
    }

    /**
     * Get the default language from the preferences.
     *
     * @param fallbackLanguage the language to fall back to if there is no preference set.
     * @return default language from the preferences (or fallback)
     */
    fun getPrefLanguage(fallbackLanguage: String): String?{
        return getSharedPrefs().getString(APP_LANGUAGE_KEY, fallbackLanguage)
    }

    /**
     * Persist the default screen mode in the preferences.
     *
     * @param screenMode the screen mode to persist (get from AppCompatDelegate!)
     */
    fun persistScreenMode(screenMode: Int) {
        getEditor().putInt(APP_SCREEN_MODE_KEY, screenMode).apply()
    }

    /**
     * Get the default screen mode from the preferences.
     * Fallback is AppCompatDelegate.MODE_NIGHT_NO (= 1)
     *
     * @return default screen mode from the preferences (or fallback)
     */
    fun getPrefScreenMode(): Int{
        return getSharedPrefs().getInt(APP_SCREEN_MODE_KEY, AppCompatDelegate.MODE_NIGHT_NO)
    }

    // Get the editor for the shared preferences
    private fun getEditor(): SharedPreferences.Editor{
        return getSharedPrefs().edit()
    }

    // Get the shared preferences on application context
    private fun getSharedPrefs(): SharedPreferences{
        val context = TichuApplication.getInstance().applicationContext
        val sharedPrefFile = context.packageName + "_preferences"
        return context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }
}