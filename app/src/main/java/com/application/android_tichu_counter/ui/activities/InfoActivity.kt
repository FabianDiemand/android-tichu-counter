package com.application.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.application.android_tichu_counter.BaseActivity
import com.application.android_tichu_counter.R

/**
 * Activity for the info/ impressum screen.
 *
 * Extends BaseActivity to be affine to in-app language changes.
 *
 * @author Devtronaut
 */
class InfoActivity : BaseActivity() {
    companion object {
        private const val TAG = "InfoActivity"
    }

    // important ui components in the layout
    private lateinit var ibBackbutton: ImageButton

    /**
     * Create view for InfoActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        setOnClickListeners()

        Log.d(TAG, "Create view.")
    }

    // Set onClickListeners
    private fun setOnClickListeners(){
        // Go back, finish activity
        ibBackbutton.setOnClickListener {
            super.onBackPressed()
        }

        Log.d(TAG, "Set OnClickListeners")
    }
}