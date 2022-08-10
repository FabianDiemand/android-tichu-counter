package io.github.devtronaut.android_tichu_counter.domain.intents

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.ScoreboardActivity

/**
 * Helper class to create intents.
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
class IntentCreator(val context: AppCompatActivity) {

    fun createScoreboardIntent(firstTeam: String, secondTeam: String): Intent {
        val intent = Intent(context, ScoreboardActivity::class.java)

        if (firstTeam != "") {
            intent.putExtra(ScoreboardActivity.KEY_TEAM_1, firstTeam)
        }

        if (secondTeam != "") {
            intent.putExtra(ScoreboardActivity.KEY_TEAM_2, secondTeam)
        }

        return intent
    }
}