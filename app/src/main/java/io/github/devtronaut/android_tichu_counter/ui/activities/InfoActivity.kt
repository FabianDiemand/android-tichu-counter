package io.github.devtronaut.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import io.github.devtronaut.android_tichu_counter.databinding.ActivityInfoBinding

/**
 * Activity for the info/ impressum screen.
 * Extends BaseActivity to be affine to in-app language changes.
 *
 * Copyright (C) 2022  Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Find a copy of the GNU GPL in the root-level file "LICENCE".
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