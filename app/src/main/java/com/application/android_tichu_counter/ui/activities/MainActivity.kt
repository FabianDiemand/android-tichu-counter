package com.application.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import com.application.android_tichu_counter.TichuApplication
import com.application.android_tichu_counter.databinding.ActivityMainBinding
import com.application.android_tichu_counter.domain.intents.IntentActionHelper
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_1
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_2
import com.application.android_tichu_counter.ui.fragments.TeamNameDialogFragment
import javax.inject.Inject

/**
 * MainActivity for the starting screen of the application.
 *
 * Extends BaseActivity to be affine to in-app language changes.
 * Implements TeamNameDialogListener to listen to TeamNameDialog inputs.
 *
 * @property KEY_TEAM_1 intent key for team name 1
 * @property KEY_TEAM_2 intent key for team name 2
 *
 * @author Devtronaut
 */
class MainActivity : BaseActivity(), TeamNameDialogFragment.TeamNameDialogListener {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var intentActionHelper: IntentActionHelper

    /**
     * Create view, instantiate ui components, set listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as TichuApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()

        Log.d(TAG, "Create view.")
    }

    // Set listeners for the important ui components
    private fun setOnClickListeners() {
        with(binding) {
            mbNewgame.setOnClickListener {
                showTeamNameDialog()
                Log.d(TAG, "New Game Clicked -> Requesting Team Names.")
            }

            mbLoadgame.setOnClickListener {
                intentActionHelper.startLoadGamesActivity(this@MainActivity)
                Log.d(TAG, "Load Game Clicked -> Starting LoadGameActivity.")
            }

            mbSettings.setOnClickListener {
                intentActionHelper.startSettingsActivity(this@MainActivity)
                Log.d(TAG, "Settings Clicked -> Starting SettingsActivity.")
            }

            fabInfo.setOnClickListener {
                intentActionHelper.startInfoActivity(this@MainActivity)
                Log.d(TAG, "Info Clicked -> Starting InfoActivity")
            }
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    // Show the team name dialog fragment
    private fun showTeamNameDialog() {
        TeamNameDialogFragment(this).showDialog(TAG)

        Log.d(TAG, "Showing team name dialog.")
    }

    /**
     * Retrieve team names from the inputs and pass them in an intent to the scoreboard activity.
     * Dismiss the dialog.
     */
    override fun onDialogSaveClicked(teamName1: String, teamName2: String) {
        intentActionHelper.startScoreboardActivityWithNames(this, teamName1, teamName2)

        Log.d(TAG, "Save team names clicked.")
    }
}