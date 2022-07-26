package com.application.android_tichu_counter.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.os.LocaleListCompat
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.domain.language.LocaleHelper

class SettingsActivity : AppCompatActivity() {

    companion object {
        var TAG = "SettingsActivity"
    }

    private lateinit var ibBackbutton: ImageButton
    private lateinit var ibSwissGerman: ImageButton
    private lateinit var ibGerman: ImageButton
    private lateinit var ibEnglish: ImageButton
    private lateinit var swScreenMode: SwitchCompat

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        ibBackbutton = findViewById(R.id.ib_backbutton)
        ibSwissGerman = findViewById(R.id.ib_swiss_german)
        ibGerman = findViewById(R.id.ib_german)
        ibEnglish = findViewById(R.id.ib_english)
        swScreenMode = findViewById(R.id.sw_screen_mode_switch)

        setOnClickListeners()
        Log.d(TAG, "Create View.")
    }

    private fun setOnClickListeners(){
        ibBackbutton.setOnClickListener {
            this.onBackPressed()
        }

        ibSwissGerman.setOnClickListener {
            changeAppLanguage(it)
        }

        ibGerman.setOnClickListener {
            changeAppLanguage(it)
        }

        ibEnglish.setOnClickListener{
            changeAppLanguage(it)
        }

        Log.d(MainActivity.TAG, "Set OnClickListeners")
    }

    private fun changeAppLanguage(it: View){
        when (it.id) {
            ibSwissGerman.id -> {
            }
            ibGerman.id -> {

            }
            ibEnglish.id -> {

            }
        }
    }

    private fun changeScreenMode(){

    }
}