package com.application.android_tichu_counter

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.android_tichu_counter.domain.locale.LocaleUtils
import com.application.android_tichu_counter.domain.locale.LocaleUtils.setAppLocale
import com.application.android_tichu_counter.domain.screen_mode.ScreenModeUtils

/**
 * Base activity that all other activities must extend in order to support per-app language and per-app screen mode.
 */
open class BaseActivity: AppCompatActivity() {

    /**
     * Wraps the context to ensure usage of the default language.
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ContextWrapper(newBase.setAppLocale(LocaleUtils.getDefaultLanguage())))
    }

    /**
     * Sets the screen mode on every activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(ScreenModeUtils) {
            setScreenMode(getScreenMode())
        }
    }
}