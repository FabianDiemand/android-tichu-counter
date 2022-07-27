package com.application.android_tichu_counter.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.application.android_tichu_counter.R

class SettingsActivity : AppCompatActivity() {

    companion object {
        var TAG = "SettingsActivity"
    }

    private lateinit var ibBackbutton: ImageButton
    private lateinit var ibSwissGerman: ImageButton
    private lateinit var ibGerman: ImageButton
    private lateinit var ibEnglish: ImageButton
    private lateinit var swScreenMode: SwitchCompat

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

    private fun instantiateUi(){
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            swScreenMode.isChecked = true
        }
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

        swScreenMode.setOnClickListener {
            changeScreenMode(swScreenMode.isChecked)
        }

        Log.d(MainActivity.TAG, "Set OnClickListeners")
    }

    private fun changeAppLanguage(it: View){

        when (it.id) {
            ibSwissGerman.id -> {
                Toast.makeText(this, "Language: Swiss German (gsw)", Toast.LENGTH_SHORT).show()
            }
            ibGerman.id -> {
                Toast.makeText(this, "Language: German (de)", Toast.LENGTH_SHORT).show()
            }
            ibEnglish.id -> {
                Toast.makeText(this, "Language: English (en)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeScreenMode(darkMode: Boolean){
        if(darkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}