package com.application.android_tichu_counter.domain.intents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.application.android_tichu_counter.ui.activities.BaseActivity
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity

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