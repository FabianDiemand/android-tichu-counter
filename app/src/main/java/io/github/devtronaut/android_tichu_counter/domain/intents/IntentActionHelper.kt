package io.github.devtronaut.android_tichu_counter.domain.intents

import android.content.Intent
import io.github.devtronaut.android_tichu_counter.ui.activities.BaseActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.InfoActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.LoadGameActivity
import io.github.devtronaut.android_tichu_counter.ui.activities.SettingsActivity
import javax.inject.Inject

/**
 * Helper class to wrap start activity logic.
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
class IntentActionHelper @Inject constructor() {

    fun startScoreboardWithNames(context: BaseActivity, teamName1: String, teamName2: String) {
        val intent = IntentCreator(context).createScoreboardIntent(teamName1, teamName2)
        context.startActivity(intent)
    }

    fun startLoadGames(context: BaseActivity) {
        val intent = Intent(context, LoadGameActivity::class.java)
        context.startActivity(intent)
    }

    fun startSettings(context: BaseActivity) {
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }

    fun startInfo(context: BaseActivity) {
        val intent = Intent(context, InfoActivity::class.java)
        context.startActivity(intent)
    }
}