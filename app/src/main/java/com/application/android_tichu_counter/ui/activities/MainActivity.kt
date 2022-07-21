package com.application.android_tichu_counter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.ui.fragments.TeamNameDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TeamNameDialogFragment.TeamNameDialogListener {

    companion object {
        var TAG = "MainActivity"
        var TEAM_1 = "Team1"
        var TEAM_2 = "Team2"
    }

    private lateinit var bNewGame: MaterialButton
    private lateinit var bLoadGame: MaterialButton
    private lateinit var bSettings: MaterialButton
    private lateinit var fabInfo: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bNewGame = findViewById(R.id.mb_newgame)
        bLoadGame = findViewById(R.id.mb_loadgame)
        bSettings = findViewById(R.id.mb_settings)
        fabInfo = findViewById(R.id.fab_info)

        setOnClickListeners()

        Log.d(TAG, "Create view.")
    }

    private fun setOnClickListeners(){
        bNewGame.setOnClickListener {
            startGame()
        }

        bLoadGame.setOnClickListener {
            loadGame()
        }

        bSettings.setOnClickListener {
            startSettings()
        }

        fabInfo.setOnClickListener {
            startInfo()
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    private fun startGame(){
        Toast.makeText(this, "New Game", Toast.LENGTH_SHORT).show()
        showTeamNameDialog()

        Log.d(TAG, "Start Game clicked.")
    }

    private fun loadGame(){
        Toast.makeText(this, "Load Game", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Load Game clicked.")
    }

    private fun startSettings(){
        Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

        Log.d(TAG, "Settings clicked.")
    }

    private fun startInfo(){
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)

        Log.d(TAG, "Displaying info/ impressum")
    }

    private fun showTeamNameDialog(){
        val dialog = TeamNameDialogFragment()
        dialog.show(supportFragmentManager, "TeamNameDialogFragment")

        Log.d(TAG, "Showing team name dialog.")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val team1 = dialog.dialog?.findViewById<EditText>(R.id.et_dialog_name_team1)?.text.toString()
        val team2 = dialog.dialog?.findViewById<EditText>(R.id.et_dialog_name_team2)?.text.toString()

        val intent = Intent(this, ScoreboardActivity::class.java)

        if(team1 != ""){
            intent.putExtra(TEAM_1, team1)
        }

        if(team2 != ""){
            intent.putExtra(TEAM_2, team2)
        }

        startActivity(intent)

        Log.d(TAG, "$team1 $team2")

        dialog.dismiss()
        Log.d(TAG, "Dialog gone after saving team names.")

        Log.d(TAG, "Save team names clicked.")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()

        Log.d(TAG, "Dialog dismissed.")
    }
}