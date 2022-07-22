package com.application.android_tichu_counter.ui.activities

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.application.android_tichu_counter.R
import com.google.android.material.button.MaterialButton
import com.shawnlin.numberpicker.NumberPicker
import java.security.InvalidParameterException

class ScoreboardActivity : AppCompatActivity() {

    companion object {
        var TAG = "ScoreboardActivity"
    }

    private lateinit var ibBackbutton: ImageButton

    private lateinit var tvFirstTeam: TextView
    private lateinit var tvSecondTeam: TextView

    private lateinit var tvFirstScore: TextView
    private lateinit var tvSecondScore: TextView

    private lateinit var npScorePicker: NumberPicker

    private lateinit var llButtonContainer: LinearLayout
    private lateinit var bT: MaterialButton
    private lateinit var bGT: MaterialButton
    private lateinit var bDoubleWin: MaterialButton

    private var tichu = false
    private var grandTichu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        tvFirstTeam = findViewById(R.id.tv_teamname1)
        tvSecondTeam = findViewById(R.id.tv_teamname2)

        tvFirstScore = findViewById(R.id.tv_score1)
        tvSecondScore = findViewById(R.id.tv_score2)

        llButtonContainer = findViewById(R.id.ll_scoreboard_buttons)
        bT = findViewById(R.id.b_tichu)
        bGT = findViewById(R.id.b_grandtichu)
        bDoubleWin = findViewById(R.id.b_doublewin)

        npScorePicker = findViewById(R.id.np_scorepicker)

        processIntent()
        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    private fun setListeners(){
        ibBackbutton.setOnClickListener {
            super.onBackPressed()
        }

        bT.setOnClickListener{
            setTichu()
        }

        bGT.setOnClickListener{
            setGrandTichu()
        }

        bDoubleWin.setOnClickListener{
            setDoubleWin()
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    private fun processIntent(){
        if(intent.hasExtra(MainActivity.TEAM_1)){
            tvFirstTeam.text = intent.getStringExtra(MainActivity.TEAM_1)
        } else {
            tvFirstTeam.text = getString(R.string.default_teamname_1)
        }

        if(intent.hasExtra(MainActivity.TEAM_2)){
            tvSecondTeam.text = intent.getStringExtra(MainActivity.TEAM_2)
        } else {
            tvSecondTeam.text = getString(R.string.default_teamname_2)
        }
    }

    private fun initializeUi(){
        tvFirstScore.text = "0"
        tvSecondScore.text = "0"

        val bonzai: Typeface? = ResourcesCompat.getFont(this, R.font.bonzai)
        npScorePicker.typeface = bonzai
        npScorePicker.setSelectedTypeface(bonzai)

        npScorePicker.minValue = 0
        npScorePicker.maxValue = 25
        npScorePicker.displayedValues = resources.getStringArray(R.array.scores)
    }

    private fun calculateScore(currScore: TextView, delta: EditText){
        (getInt(currScore) + getInt(delta)).toString()
            .also { currScore.text = it }

        delta.text.clear()
    }

    private fun getInt(view: View): Int {
        return when (view) {
            is EditText  -> {
                if(view.text.isNotBlank()){
                    view.text.toString().trim().toInt()
                } else{
                    0
                }
            }
            is TextView -> {
                if(view.text.isNotBlank()){
                    view.text.toString().trim().toInt()
                } else{
                    0
                }
            }
            else -> {
                throw InvalidParameterException()
            }
        }
    }

    private fun setTichu(){
        tichu = true
        grandTichu = false

        bT.isEnabled = false
        bGT.isEnabled = true
    }

    private fun setGrandTichu(){
        grandTichu = true
        tichu = false

        bGT.isEnabled = false
        bT.isEnabled = true
    }

    private fun setDoubleWin(){
        bGT.isEnabled = true
        bT.isEnabled = true
    }

//    private fun setDoubleWinScore(currScore: TextView){
//        var delta = 0
//        val doubleWin = 200
//
//        if(tichu){
//            delta = 100
//        } else if(grandTichu){
//            delta = 200
//        }
//
//        (getInt(currScore) + doubleWin + delta).toString()
//            .also { currScore.text = it }
//    }
}