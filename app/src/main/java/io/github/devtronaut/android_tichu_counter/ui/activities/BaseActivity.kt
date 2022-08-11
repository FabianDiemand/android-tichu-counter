package io.github.devtronaut.android_tichu_counter.ui.activities

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.github.devtronaut.android_tichu_counter.domain.locale.LocaleUtils
import io.github.devtronaut.android_tichu_counter.domain.locale.LocaleUtils.setAppLocale
import io.github.devtronaut.android_tichu_counter.domain.screen_mode.ScreenModeUtils
import javax.inject.Inject

/**
 * Base activity that all other activities must extend in order to support per-app language and per-app screen mode.
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
open class BaseActivity @Inject constructor() : AppCompatActivity() {
    companion object {
        private const val TAG = "BaseActivity"
    }

    /**
     * Wraps the context to ensure usage of the default language.
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale(LocaleUtils.getDefaultLocale())))

        Log.d(TAG, "Add wrapped context with applications locale (default language)")
    }

    /**
     * Sets the screen mode on every activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ScreenModeUtils) {
            setScreenMode(getScreenMode())
        }

        Log.d(TAG, "Create activity in default screen mode.")
    }
}