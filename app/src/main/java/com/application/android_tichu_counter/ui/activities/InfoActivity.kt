package com.application.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import com.application.android_tichu_counter.databinding.ActivityInfoBinding

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

    private lateinit var binding: ActivityInfoBinding

    /**
     * Create view for InfoActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()

        Log.d(TAG, "Create view.")
    }

    // Set onClickListeners
    private fun setOnClickListeners(){
        binding.ibBackbutton.setOnClickListener {
            super.onBackPressed()
        }

        Log.d(TAG, "Set OnClickListeners")
    }
}