package io.github.devtronaut.android_tichu_counter.domain.screen_mode

import androidx.appcompat.app.AppCompatDelegate
import io.github.devtronaut.android_tichu_counter.domain.preferences.PreferenceUtils

/**
 * Object with utility functions to handle the default screen mode of the application.
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