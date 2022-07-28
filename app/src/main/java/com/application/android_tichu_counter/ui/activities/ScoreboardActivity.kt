package com.application.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import com.application.android_tichu_counter.BaseActivity
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.ui.activities.MainActivity.Companion.TEAM_1
import com.application.android_tichu_counter.ui.activities.MainActivity.Companion.TEAM_2
import com.application.android_tichu_counter.ui.fragments.SetScoreFragment

/**
 * ScoreboardActivity to display points and game progress
 *
 * Extends BaseActivity to be affine to in-app language changes.
 * Implements SetScoreListener to listen to inputs in the SetScoreFragment.
 *
 * @property TEAM_1 intent key for team name 1
 * @property TEAM_2 intent key for team name 2
 *
 * @author Devtronaut
 */
class ScoreboardActivity : BaseActivity(), SetScoreFragment.SetScoreListener {
    companion object {
        private const val TAG = "ScoreboardActivity"
    }

    // Important ui components
    private lateinit var ibBackbutton: ImageButton

    private lateinit var vGrayBackground: View

    private lateinit var llFirstTeamScoreboard: LinearLayout
    private lateinit var llSecondTeamScoreboard: LinearLayout

    private lateinit var tvFirstTeamName: TextView
    private lateinit var tvSecondTeamName: TextView

    private lateinit var tvFirstTeamScore: TextView
    private lateinit var tvSecondTeamScore: TextView

    private lateinit var fcvScoreFragmentContainer: FragmentContainerView

    // Activity variables
    private var setScoreFragment: SetScoreFragment? = null

    private var scoringTeamId: Int = -1

    inner class ScoreBonus {
        var tichuSuccess = false
        var tichuFailure = false
        var grandTichuSuccess = false
        var grandTichuFailure = false
    }

    private var teamScore: ScoreBonus? = null
    private var oppTeamScore: ScoreBonus? = null

    /**
     * Create view, instantiate ui components, set listeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        tvFirstTeamName = findViewById(R.id.tv_teamname1)
        tvSecondTeamName = findViewById(R.id.tv_teamname2)

        tvFirstTeamScore = findViewById(R.id.tv_score1)
        tvSecondTeamScore = findViewById(R.id.tv_score2)

        llFirstTeamScoreboard = findViewById(R.id.ll_team1)
        llSecondTeamScoreboard = findViewById(R.id.ll_team2)

        fcvScoreFragmentContainer = findViewById(R.id.scoreFragment)
        vGrayBackground = findViewById(R.id.gray_background)

        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    // Set listeners to important ui components
    private fun setListeners() {
        ibBackbutton.setOnClickListener {
            onBackPressed()
        }

        llFirstTeamScoreboard.setOnClickListener {
            showSetScoreDialog(tvFirstTeamName.text.toString(), tvSecondTeamName.text.toString())
            scoringTeamId = it.id
        }

        llSecondTeamScoreboard.setOnClickListener {
            showSetScoreDialog(tvSecondTeamName.text.toString(), tvFirstTeamName.text.toString())
            scoringTeamId = it.id
        }

        vGrayBackground.setOnClickListener {
            onRemoveClicked(setScoreFragment!!)
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    // Initialize the scores and the team names
    private fun initializeUi() {
        processIntent()

        tvFirstTeamScore.text = "0"
        tvSecondTeamScore.text = "0"
    }

    // Read the teamnames from the intent or set defaults if no teamnames are put in the extras
    private fun processIntent() {
        if (intent.hasExtra(TEAM_1)) {
            tvFirstTeamName.text = intent.getStringExtra(TEAM_1)
        } else {
            tvFirstTeamName.text = getString(R.string.default_teamname_1)
        }

        if (intent.hasExtra(TEAM_2)) {
            tvSecondTeamName.text = intent.getStringExtra(TEAM_2)
        } else {
            tvSecondTeamName.text = getString(R.string.default_teamname_2)
        }
    }

    /**
     * Remove the gray view from the screen and finish the activity
     */
    override fun onBackPressed() {
        vGrayBackground.visibility = View.GONE

        super.onBackPressed()
    }

    /**
     * Change the round state variables for tichu according to the user input
     */
    override fun onTichuClicked(setScoreFragment: SetScoreFragment) {
        setTichu(teamScore!!)
    }

    /**
     * Change the round state variables for opponent tichu according to the user input
     */
    override fun onOppTichuClicked(setScoreFragment: SetScoreFragment) {
        setTichu(oppTeamScore!!)
    }

    /**
     * Change the round state variables for grand tichu according to the user input
     */
    override fun onGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        setGrandTichu(teamScore!!)
    }

    /**
     * Change the round state variables for opponent grand tichu according to the user input
     */
    override fun onOppGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        setGrandTichu(oppTeamScore!!)
    }

    /**
     * Calculate the new score with a double win
     */
    override fun onDoubleWinClicked(setScoreFragment: SetScoreFragment) {
        // Point reward for a double win
        val doubleWinPoints = 200

        if (scoringTeamId == llFirstTeamScoreboard.id) {
            // If first team is entering points, give them the double win.
            setScore(tvFirstTeamScore, calculateTeamScore(doubleWinPoints))
            setScore(tvSecondTeamScore, calculateOppScore(0))
        } else if (scoringTeamId == llSecondTeamScoreboard.id) {
            // If second team is entering points, give them the double win.
            setScore(tvSecondTeamScore, calculateTeamScore(doubleWinPoints))
            setScore(tvFirstTeamScore, calculateOppScore(0))
        }

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)
    }

    /**
     * Calculate the new score on a normal round (no double win)
     */
    override fun onOkClicked(setScoreFragment: SetScoreFragment, value: Int) {
        if (scoringTeamId == llFirstTeamScoreboard.id) {
            // Calculate scores in respect to which team is entering their result
            val oppRoundPoints = 100 - value
            setScore(tvSecondTeamScore, calculateOppScore(oppRoundPoints))

            setScore(tvFirstTeamScore, calculateTeamScore(value))
        } else if (scoringTeamId == llSecondTeamScoreboard.id) {
            // Calculate scores in respect to which team is entering their result
            setScore(tvFirstTeamScore, calculateOppScore(value))

            val oppRoundPoints = 100 - value
            setScore(tvSecondTeamScore, calculateTeamScore(oppRoundPoints))
        }

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)
    }

    /**
     * Reset all the state variables, enable UIs
     */
    override fun onRemoveClicked(setScoreFragment: SetScoreFragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .remove(setScoreFragment)
            .commit()


        supportFragmentManager.popBackStack()

        teamScore = null
        oppTeamScore = null
        vGrayBackground.visibility = View.GONE
        flipComponentsEnabled()
    }

    // Show the SetScoreDialog with focus on the team that enters its score
    private fun showSetScoreDialog(teamName: String, oppTeamName: String) {
        setScoreFragment = SetScoreFragment.getInstance(teamName, oppTeamName)

        supportFragmentManager.beginTransaction()
            // Set slide-in/ slide-out animations
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .replace(R.id.scoreFragment, setScoreFragment!!)
            .addToBackStack(setScoreFragment!!.tag)
            .commit()

        // Make gray background visible to simulate dialog behaviour
        vGrayBackground.visibility = View.VISIBLE
        // Disable views in the background
        flipComponentsEnabled()

        teamScore = ScoreBonus()
        oppTeamScore = ScoreBonus()
    }


    private fun setTichu(score: ScoreBonus) {
        with(score) {
            // can't be tichu and grandTichu at the same time
            grandTichuSuccess = false

            if (!tichuSuccess) {
                // no tichu set before -> first click -> success state
                tichuSuccess = true
                tichuFailure = false
            } else if (tichuFailure) {
                // tichu failure already set -> third click -> neutral state
                tichuSuccess = false
                tichuFailure = false
            } else {
                // tichu was set -> second click -> failure state
                tichuSuccess = false
                tichuFailure = true
            }
        }
    }

    private fun setGrandTichu(score: ScoreBonus) {
        with(score) {
            // can't be grandTichu and tichu at the same time
            tichuSuccess = false

            if (!grandTichuSuccess) {
                // no grandTichu set before -> first click -> success state
                grandTichuSuccess = true
                grandTichuFailure = false
            } else if (grandTichuFailure) {
                // grandTichu failure already set -> third click -> neutral state
                grandTichuSuccess = false
                grandTichuFailure = false
            } else {
                // grandTichu was set -> second click -> failure state
                grandTichuSuccess = false
                grandTichuFailure = true
            }
        }
    }

    // Calculate the score of the team entering the points
    private fun calculateTeamScore(score: Int): Int {
        return calculateScore(teamScore!!, score)
    }

    // Calculate the score of the team opposing the one entering the points
    private fun calculateOppScore(score: Int): Int {
        return calculateScore(oppTeamScore!!, score)
    }

    private fun calculateScore(bonus: ScoreBonus, score: Int): Int {
        with(bonus) {
            if (tichuSuccess) {
                return score + 100
            } else if (grandTichuSuccess) {
                return score + 200
            } else if (tichuFailure) {
                return score - 100
            } else if (grandTichuFailure) {
                return score - 200
            }
        }

        return score
    }

    // Flip enabled state of layout key components
    private fun flipComponentsEnabled() {
        ibBackbutton.isEnabled = !ibBackbutton.isEnabled
        llFirstTeamScoreboard.isEnabled = !llFirstTeamScoreboard.isEnabled
        llSecondTeamScoreboard.isEnabled = !llSecondTeamScoreboard.isEnabled
    }

    // Set the score to the passed textview
    private fun setScore(scoreView: TextView, delta: Int) {
        (getInt(scoreView) + delta).toString()
            .also { scoreView.text = it }
    }

    // Get the value of a textview as an Int
    private fun getInt(textView: TextView): Int {
        return if (textView.text.isNotBlank()) {
            textView.text.toString().trim().toInt()
        } else {
            0
        }
    }
}