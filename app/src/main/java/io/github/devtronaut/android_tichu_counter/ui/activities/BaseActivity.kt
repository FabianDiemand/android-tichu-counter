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
 * @author Devtronaut
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