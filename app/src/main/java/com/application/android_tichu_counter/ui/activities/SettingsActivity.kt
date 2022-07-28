package com.application.android_tichu_counter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.SwitchCompat
import com.application.android_tichu_counter.BaseActivity
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.domain.locale.LocaleUtils
import com.application.android_tichu_counter.domain.screen_mode.ScreenModeUtils

/**
 * SettingsActivity to allow user to change screen mode and in-app language
 *
 * Extends BaseActivity to be affine to in-app language changes.
 *
 * @author Devtronaut
 */
class SettingsActivity : BaseActivity() {
    companion object {
        private const val TAG = "SettingsActivity"
    }

    // Important UI Components
    private lateinit var ibBackbutton: ImageButton
    private lateinit var ibSwissGerman: ImageButton
    private lateinit var ibGerman: ImageButton
    private lateinit var ibEnglish: ImageButton
    private lateinit var swScreenMode: SwitchCompat

    /**
     * Create view, instantiate ui components, set listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ibBackbutton = findViewById(R.id.ib_backbutton)
        ibSwissGerman = findViewById(R.id.ib_swiss_german)
        ibGerman = findViewById(R.id.ib_german)
        ibEnglish = findViewById(R.id.ib_english)
        swScreenMode = findViewById(R.id.sw_screen_mode_switch)

        instantiateUi()
        setOnClickListeners()

        Log.d(TAG, "Create View.")
    }

    // Flip the switch according to the current screen mode
    private fun instantiateUi() {
        swScreenMode.isChecked = ScreenModeUtils.isNightMode()

        if (LocaleUtils.getDefaultLanguage() == LocaleUtils.LANG_GERMAN) {
            disableButton(ibGerman)
        } else if (LocaleUtils.getDefaultLanguage() == LocaleUtils.LANG_SWISS_GERMAN) {
            disableButton(ibSwissGerman)
        } else if (LocaleUtils.getDefaultLanguage() == LocaleUtils.LANG_ENGLISH) {
            disableButton(ibEnglish)
        }
    }

    // Set listeners for important UI components
    private fun setOnClickListeners() {
        ibBackbutton.setOnClickListener {
            this.onBackPressed()
        }

        ibSwissGerman.setOnClickListener {
            changeAppLanguage(it)
        }

        ibGerman.setOnClickListener {
            changeAppLanguage(it)
        }

        ibEnglish.setOnClickListener {
            changeAppLanguage(it)
        }

        swScreenMode.setOnClickListener {
            changeScreenMode(swScreenMode.isChecked)
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    // Change application language in respect to the users choice
    private fun changeAppLanguage(it: View) {

        when (it.id) {
            ibSwissGerman.id -> {
                LocaleUtils.persistDefaultLanguage(LocaleUtils.LANG_SWISS_GERMAN)
            }
            ibGerman.id -> {
                LocaleUtils.persistDefaultLanguage(LocaleUtils.LANG_GERMAN)
            }
            ibEnglish.id -> {
                LocaleUtils.persistDefaultLanguage(LocaleUtils.LANG_ENGLISH)
            }
        }

        restartApplication()
    }

    // Restart the application
    private fun restartApplication() {
        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
        i?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
    }

    // Change the screen mode
    private fun changeScreenMode(darkMode: Boolean) {
        ScreenModeUtils.changeScreenMode(darkMode)
    }

    private fun disableButton(button: ImageButton) {
        button.alpha = .3f
        button.isEnabled = false
    }
}