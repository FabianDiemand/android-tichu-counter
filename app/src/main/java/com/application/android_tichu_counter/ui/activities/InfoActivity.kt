package com.application.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.application.android_tichu_counter.BaseActivity
import com.application.android_tichu_counter.R

class InfoActivity : BaseActivity() {

    companion object {
        var TAG = "InfoActivity"
    }

    private lateinit var ibBackbutton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        setOnClickListeners()

        Log.d(TAG, "Create view.")
    }

    private fun setOnClickListeners(){
        ibBackbutton.setOnClickListener {
            super.onBackPressed()
        }

        Log.d(TAG, "Set OnClickListeners")
    }
}