package com.application.android_tichu_counter.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.application.android_tichu_counter.R
import com.google.android.material.button.MaterialButton
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

    private lateinit var etFirstScore: EditText
    private lateinit var etSecondScore: EditText

    private lateinit var llButtonContainer: LinearLayout
    private lateinit var bT: MaterialButton
    private lateinit var bGT: MaterialButton
    private lateinit var bDoubleWin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        tvFirstTeam = findViewById(R.id.tv_teamname1)
        tvSecondTeam = findViewById(R.id.tv_teamname2)

        tvFirstScore = findViewById(R.id.tv_score1)
        tvSecondScore = findViewById(R.id.tv_score2)

        etFirstScore = findViewById(R.id.et_number_score1)
        etSecondScore = findViewById(R.id.et_number_score2)

        llButtonContainer = findViewById(R.id.ll_scoreboard_buttons)
        bT = findViewById(R.id.b_tichu)
        bGT = findViewById(R.id.b_grandtichu)
        bDoubleWin = findViewById(R.id.b_doublewin)

        processIntent()
        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    private fun setListeners(){
        ibBackbutton.setOnClickListener {
            super.onBackPressed()
        }

        etFirstScore.setOnClickListener{
            llButtonContainer.visibility = View.VISIBLE
            newScore(it as EditText)
        }

        etSecondScore.setOnClickListener{
            llButtonContainer.visibility = View.VISIBLE
            newScore(it as EditText)
        }

        etFirstScore.setOnFocusChangeListener{_, hasFocus ->
            if(hasFocus){
                Toast.makeText(this, "Gained Focus", Toast.LENGTH_SHORT).show()
                llButtonContainer.visibility = View.VISIBLE
            }
        }

        etSecondScore.setOnFocusChangeListener{_, hasFocus ->
            if(hasFocus){
                Toast.makeText(this, "Gained Focus", Toast.LENGTH_SHORT).show()
                llButtonContainer.visibility = View.VISIBLE
            }
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
    }

    private fun newScore(v: EditText){
        when(v){
            etFirstScore -> calculateScore(tvFirstScore, etFirstScore)
            etSecondScore -> calculateScore(tvSecondScore, etSecondScore)
        }
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
        bT.isEnabled = false
        bGT.isEnabled = true
    }

    private fun setGrandTichu(){
        bGT.isEnabled = false
        bT.isEnabled = true
    }

    private fun setDoubleWin(){
        bDoubleWin.isEnabled = false
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            llButtonContainer.visibility = View.GONE
        } else {
            llButtonContainer.visibility = View.VISIBLE
        }

        return super.dispatchTouchEvent(ev)
    }


}