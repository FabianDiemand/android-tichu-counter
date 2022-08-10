package io.github.devtronaut.android_tichu_counter.domain.screen_mode

import androidx.appcompat.app.AppCompatDelegate
import io.github.devtronaut.android_tichu_counter.domain.preferences.PreferenceUtils

/**
 * Object with utility functions to handle the default screen mode of the application.
 *
 * @author Devtronaut
 */
object ScreenModeUtils {
    /**
     * Change the screen mode according to a boolean value.
     * Designed to work with a switch widget.
     *
     * @param darkMode true to set dark mode/ false to set light mode
     */
    fun changeScreenMode(darkMode: Boolean){
        if(darkMode){
            setScreenMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setScreenMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    /**
     * Get the default screen mode.
     *
     * @return integer representing the current screen mode
     *
     * @see AppCompatDelegate.MODE_NIGHT_YES
     * @see AppCompatDelegate.MODE_NIGHT_NO
     */
    fun getScreenMode(): Int{
        return PreferenceUtils.getPrefScreenMode()
    }

    /**
     * Set the default screen mode and persist the new mode in the apps preferences.
     *
     * @param screenMode integer representing the current screen mode
     *
     * @see AppCompatDelegate.MODE_NIGHT_YES
     * @see AppCompatDelegate.MODE_NIGHT_NO
     */
    fun setScreenMode(screenMode: Int){
        AppCompatDelegate.setDefaultNightMode(screenMode)
        PreferenceUtils.persistScreenMode(screenMode)
    }

    /**
     * Checks whether the app is in night/ dark mode
     *
     * @return true if in night mode, false otherwise
     */
    fun isNightMode(): Boolean{
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }
}