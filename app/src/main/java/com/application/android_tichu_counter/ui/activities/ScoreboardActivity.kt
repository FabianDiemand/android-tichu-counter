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

    // Round state variables
    private var tichuSuccess = false
    private var tichuFailure = false
    private var grandTichuSuccess = false
    private var grandTichuFailure = false

    private var oppTichuSuccess = false
    private var oppTichuFailure = false
    private var oppGrandTichuSuccess = false
    private var oppGrandTichuFailure = false

    private var scoringTeamId: Int = -1

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
    }

    /**
     * Change the round state variables for tichu according to the user input
     */
    override fun onTichuClicked(setScoreFragment: SetScoreFragment) {
        // can't be tichu and grandTichu at the same time
        grandTichuSuccess = false

        if(!tichuSuccess){
            // no tichu set before -> first click -> success state
            tichuSuccess = true
            tichuFailure = false
        } else if(tichuFailure){
            // tichu failure already set -> third click -> neutral state
            tichuSuccess = false
            tichuFailure = false
        } else {
            // tichu was set -> second click -> failure state
            tichuSuccess = false
            tichuFailure = true
        }
    }

    /**
     * Change the round state variables for grand tichu according to the user input
     */
    override fun onGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        // can't be grandTichu and tichu at the same time
        tichuSuccess = false

        if(!grandTichuSuccess){
            // no grandTichu set before -> first click -> success state
            grandTichuSuccess = true
            grandTichuFailure = false
        } else if(grandTichuFailure){
            // grandTichu failure already set -> third click -> neutral state
            grandTichuSuccess = false
            grandTichuFailure = false
        } else {
            // grandTichu was set -> second click -> failure state
            grandTichuSuccess = false
            grandTichuFailure = true
        }
    }

    /**
     * Change the round state variables for opponent tichu according to the user input
     */
    override fun onOppTichuClicked(setScoreFragment: SetScoreFragment) {
        // can't be tichu and grandTichu at the same time
        oppGrandTichuSuccess = false

        if(!oppTichuSuccess){
            // no tichu set before -> first click -> success state
            oppTichuSuccess = true
            oppTichuFailure = false
        } else if(oppTichuFailure){
            // tichu failure already set -> third click -> neutral state
            oppTichuSuccess = false
            oppTichuFailure = false
        } else {
            // tichu was set -> second click -> failure state
            oppTichuSuccess = false
            oppTichuFailure = true
        }
    }

    /**
     * Change the round state variables for opponent grand tichu according to the user input
     */
    override fun onOppGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        // can't be grandTichu and tichu at the same time
        oppTichuSuccess = false

        if(!oppGrandTichuSuccess){
            // no grandTichu set before -> first click -> success state
            oppGrandTichuSuccess = true
            oppGrandTichuFailure = false
        } else if(oppGrandTichuFailure){
            // grandTichu failure already set -> third click -> neutral state
            oppGrandTichuSuccess = false
            oppGrandTichuFailure = false
        } else {
            // grandTichu was set -> second click -> failure state
            oppGrandTichuSuccess = false
            oppGrandTichuFailure = true
        }
    }

    /**
     * Calculate the new score with a double win
     */
    override fun onDoubleWinClicked(setScoreFragment: SetScoreFragment) {
        // Point reward for a double win
        val doubleWinPoints = 200

        if(scoringTeamId == llFirstTeamScoreboard.id) {
            // If first team is entering points, give them the double win.
            setScore(tvFirstTeamScore, calculateTeamScore(doubleWinPoints))
            setScore(tvSecondTeamScore, calculateOppScore(0))
        } else if(scoringTeamId == llSecondTeamScoreboard.id){
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
        if(scoringTeamId == llFirstTeamScoreboard.id){
            // Calculate scores in respect to which team is entering their result
            setScore(tvSecondTeamScore, calculateOppScore(value))
            setScore(tvFirstTeamScore, calculateTeamScore(value))
        } else if(scoringTeamId == llSecondTeamScoreboard.id) {
            // Calculate scores in respect to which team is entering their result
            setScore(tvFirstTeamScore, calculateOppScore(value))
            setScore(tvSecondTeamScore, calculateTeamScore(value))
        }

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)
    }

    /**
     * Reset all the state variables, enable UIs
     */
    override fun onRemoveClicked(setScoreFragment: SetScoreFragment) {
        grandTichuFailure = false
        grandTichuSuccess = false
        tichuFailure = false
        tichuSuccess = false

        oppGrandTichuFailure = false
        oppGrandTichuSuccess = false
        oppTichuFailure = false
        oppTichuSuccess = false

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .remove(setScoreFragment)
            .commit()


        supportFragmentManager.popBackStack()

        vGrayBackground.visibility = View.GONE
        flipComponentsEnabled()
    }

    // Flip enabled state of layout key components
    private fun flipComponentsEnabled(){
        ibBackbutton.isEnabled = !ibBackbutton.isEnabled
        llFirstTeamScoreboard.isEnabled = !llFirstTeamScoreboard.isEnabled
        llSecondTeamScoreboard.isEnabled = !llSecondTeamScoreboard.isEnabled
    }

    // Calculate the score of the team entering the points
    private fun calculateTeamScore(score: Int): Int{
        var v = score
        if(tichuSuccess){
            v += 100
        } else if(grandTichuSuccess){
            v += 200
        } else if(tichuFailure){
            v -= 100
        } else if(grandTichuFailure){
            v-= 200
        }

        return v
    }

    // Calculate the score of the team opposing the one entering the points
    private fun calculateOppScore(score: Int): Int{
        val v = 100 - score

        if(oppTichuSuccess){
            return v + 100
        } else if(oppGrandTichuSuccess){
            return v + 200
        } else if(oppTichuFailure){
            return v - 100
        } else if(oppGrandTichuFailure){
            return v- 200
        }

        return v
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