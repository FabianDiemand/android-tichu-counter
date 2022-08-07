package com.application.android_tichu_counter.domain.intents

import android.content.Intent
import com.application.android_tichu_counter.ui.activities.BaseActivity
import com.application.android_tichu_counter.ui.activities.InfoActivity
import com.application.android_tichu_counter.ui.activities.LoadGameActivity
import com.application.android_tichu_counter.ui.activities.SettingsActivity
import javax.inject.Inject

class IntentActionHelper @Inject constructor() {

    fun startScoreboardActivityWithNames(
        context: BaseActivity,
        teamName1: String,
        teamName2: String
    ) {
        val intent = IntentCreator(context).createScoreboardIntent(teamName1, teamName2)
        context.startActivity(intent)
    }

    fun startLoadGamesActivity(context: BaseActivity) {
        val intent = Intent(context, LoadGameActivity::class.java)
        context.startActivity(intent)
    }

    fun startSettingsActivity(context: BaseActivity) {
        val intent = Intent(context, SettingsActivity::class.java)
        context.startActivity(intent)
    }

    fun startInfoActivity(context: BaseActivity) {
        val intent = Intent(context, InfoActivity::class.java)
        context.startActivity(intent)
    }
}