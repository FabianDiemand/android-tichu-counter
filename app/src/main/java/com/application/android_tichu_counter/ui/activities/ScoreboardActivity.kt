package com.application.android_tichu_counter.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.ui.fragments.SetScoreFragment

class ScoreboardActivity : AppCompatActivity(), SetScoreFragment.SetScoreListener {

    companion object {
        var TAG = "ScoreboardActivity"
    }

    private lateinit var ibBackbutton: ImageButton

    private lateinit var tvFirstTeam: TextView
    private lateinit var tvSecondTeam: TextView

    private lateinit var tvFirstScore: TextView
    private lateinit var tvSecondScore: TextView

    private lateinit var llFirstTeamScore: LinearLayout
    private lateinit var llSecondTeamScore: LinearLayout
    private lateinit var vGrayBackground: View

    private var setScoreFragment: SetScoreFragment? = null
    private lateinit var scoreFragment: FragmentContainerView

    private var tichuSuccess = false
    private var tichuFailure = false
    private var grandTichuSuccess = false
    private var grandTichuFailure = false

    private var oppTichuSuccess = false
    private var oppTichuFailure = false
    private var oppGrandTichuSuccess = false
    private var oppGrandTichuFailure = false

    private var scoringTeam: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        tvFirstTeam = findViewById(R.id.tv_teamname1)
        tvSecondTeam = findViewById(R.id.tv_teamname2)

        tvFirstScore = findViewById(R.id.tv_score1)
        tvSecondScore = findViewById(R.id.tv_score2)

        llFirstTeamScore = findViewById(R.id.ll_team1)
        llSecondTeamScore = findViewById(R.id.ll_team2)

        scoreFragment = findViewById(R.id.scoreFragment)
        vGrayBackground = findViewById(R.id.gray_background)

        processIntent()
        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    private fun setListeners() {
        ibBackbutton.setOnClickListener {
            onBackPressed()
        }

        llFirstTeamScore.setOnClickListener {
            showSetScoreDialog(tvFirstTeam.text.toString(), tvSecondTeam.text.toString())
            scoringTeam = it.id
        }

        llSecondTeamScore.setOnClickListener {
            showSetScoreDialog(tvSecondTeam.text.toString(), tvFirstTeam.text.toString())
            scoringTeam = it.id
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    override fun onBackPressed() {
        vGrayBackground.visibility = View.GONE

        super.onBackPressed()
    }

    private fun processIntent() {
        if (intent.hasExtra(MainActivity.TEAM_1)) {
            tvFirstTeam.text = intent.getStringExtra(MainActivity.TEAM_1)
        } else {
            tvFirstTeam.text = getString(R.string.default_teamname_1)
        }

        if (intent.hasExtra(MainActivity.TEAM_2)) {
            tvSecondTeam.text = intent.getStringExtra(MainActivity.TEAM_2)
        } else {
            tvSecondTeam.text = getString(R.string.default_teamname_2)
        }
    }

    private fun initializeUi() {
        tvFirstScore.text = "0"
        tvSecondScore.text = "0"
    }

    private fun showSetScoreDialog(teamName: String, oppTeamName: String) {
        setScoreFragment = SetScoreFragment.getInstance(teamName, oppTeamName)

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .replace(R.id.scoreFragment, setScoreFragment!!)
            .addToBackStack(setScoreFragment!!.tag)
            .commit()

        vGrayBackground.visibility = View.VISIBLE

        ibBackbutton.isEnabled = false
        llFirstTeamScore.isEnabled = false
        llSecondTeamScore.isEnabled = false
    }

    private fun getInt(textView: TextView): Int {
        return if (textView.text.isNotBlank()) {
            textView.text.toString().trim().toInt()
        } else {
            0
        }
    }

    override fun onTichuClicked(setScoreFragment: SetScoreFragment) {
        grandTichuSuccess = false

        if(!tichuSuccess){
            tichuSuccess = true
            tichuFailure = false
        } else if(tichuFailure){
            tichuSuccess = false
            tichuFailure = false
        } else {
            tichuSuccess = false
            tichuFailure = true
        }
    }

    override fun onOppTichuClicked(setScoreFragment: SetScoreFragment) {
        oppGrandTichuSuccess = false

        if(!oppTichuSuccess){
            oppTichuSuccess = true
            oppTichuFailure = false
        } else if(oppTichuFailure){
            oppTichuSuccess = false
            oppTichuFailure = false
        } else {
            oppTichuSuccess = false
            oppTichuFailure = true
        }
    }

    override fun onGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        tichuSuccess = false

        if(!grandTichuSuccess){
            grandTichuSuccess = true
            grandTichuFailure = false
        } else if(grandTichuFailure){
            grandTichuSuccess = false
            grandTichuFailure = false
        } else {
            grandTichuSuccess = false
            grandTichuFailure = true
        }
    }

    override fun onOppGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        oppTichuSuccess = false

        if(!oppGrandTichuSuccess){
            oppGrandTichuSuccess = true
            oppGrandTichuFailure = false
        } else if(oppGrandTichuFailure){
            oppGrandTichuSuccess = false
            oppGrandTichuFailure = false
        } else {
            oppGrandTichuSuccess = false
            oppGrandTichuFailure = true
        }
    }

    override fun onDoubleWinClicked(setScoreFragment: SetScoreFragment) {
        val doubleWinPoints = 200

        if(scoringTeam == llFirstTeamScore.id) {
            setScore(tvFirstScore, calculateTeamScore(doubleWinPoints))
            setScore(tvSecondScore, calculateOppScore(0))
        } else if(scoringTeam == llSecondTeamScore.id){
            setScore(tvSecondScore, calculateTeamScore(doubleWinPoints))
            setScore(tvFirstScore, calculateOppScore(0))
        }

        onRemoveClicked(setScoreFragment)
    }

    override fun onOkClicked(setScoreFragment: SetScoreFragment, value: Int) {
        if(scoringTeam == llFirstTeamScore.id){
            setScore(tvSecondScore, calculateOppScore(value))
            setScore(tvFirstScore, calculateTeamScore(value))
        } else if(scoringTeam == llSecondTeamScore.id) {
            setScore(tvFirstScore, calculateOppScore(value))
            setScore(tvSecondScore, calculateTeamScore(value))
        }

        Log.d(TAG, "Tichu Success: $tichuSuccess; Tichu Failure: $tichuFailure || GrandTichu Success: $grandTichuSuccess; GrandTichu Failure: $grandTichuFailure")

        onRemoveClicked(setScoreFragment)
    }

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

    private fun calculateOppScore(score: Int): Int{
        var v = 100 - score

        if(oppTichuSuccess){
            v += 100
        } else if(oppGrandTichuSuccess){
            v += 200
        } else if(oppTichuFailure){
            v -= 100
        } else if(oppGrandTichuFailure){
            v-= 200
        }

        return v
    }

    private fun setScore(scoreView: TextView, delta: Int) {
        (getInt(scoreView) + delta).toString()
            .also { scoreView.text = it }
    }

    override fun onRemoveClicked(setScoreFragment: SetScoreFragment) {
        llFirstTeamScore.isEnabled = true
        llSecondTeamScore.isEnabled = true

        grandTichuFailure = false
        grandTichuSuccess = false
        tichuFailure = false
        tichuSuccess = false

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .remove(setScoreFragment)
            .commit()

        supportFragmentManager.popBackStack()

        vGrayBackground.visibility = View.GONE

        ibBackbutton.isEnabled = true
        llFirstTeamScore.isEnabled = true
        llSecondTeamScore.isEnabled = true
    }
}