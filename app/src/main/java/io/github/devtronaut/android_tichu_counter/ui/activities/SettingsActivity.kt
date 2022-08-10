package io.github.devtronaut.android_tichu_counter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import io.github.devtronaut.android_tichu_counter.databinding.ActivitySettingsBinding
import io.github.devtronaut.android_tichu_counter.domain.locale.LocaleUtils
import io.github.devtronaut.android_tichu_counter.domain.screen_mode.ScreenModeUtils

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

    private lateinit var binding: ActivitySettingsBinding

    /**
     * Create view, instantiate ui components, set listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        instantiateUi()
        setOnClickListeners()

        Log.d(TAG, "Create View.")
    }

    // Flip the switch according to the current screen mode
    private fun instantiateUi() {
        binding.swScreenModeSwitch.isChecked = ScreenModeUtils.isNightMode()

        if (LocaleUtils.getDefaultLocale() == LocaleUtils.LANG_GERMAN) {
            disableButton(binding.ibGerman)
        } else if (LocaleUtils.getDefaultLocale() == LocaleUtils.LANG_SWISS_GERMAN) {
            disableButton(binding.ibSwissGerman)
        } else if (LocaleUtils.getDefaultLocale() == LocaleUtils.LANG_ENGLISH) {
            disableButton(binding.ibEnglish)
        }
    }

    // Set listeners for important UI components
    private fun setOnClickListeners() {
        with(binding){
            ibBackbutton.setOnClickListener {
                onBackPressed()
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

            swScreenModeSwitch.setOnClickListener {
                changeScreenMode(swScreenModeSwitch.isChecked)
            }

        }

        Log.d(TAG, "Set OnClickListeners")
    }

    // Change application language in respect to the users choice
    private fun changeAppLanguage(it: View) {
        with(binding){
            when (it.id) {
                ibSwissGerman.id -> {
                    LocaleUtils.persistDefaultLocale(LocaleUtils.LANG_SWISS_GERMAN)
                }
                ibGerman.id -> {
                    LocaleUtils.persistDefaultLocale(LocaleUtils.LANG_GERMAN)
                }
                ibEnglish.id -> {
                    LocaleUtils.persistDefaultLocale(LocaleUtils.LANG_ENGLISH)
                }
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