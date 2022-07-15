package com.application.android_tichu_counter

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.application.android_tichu_counter.fragments.TeamNameDialogFragment
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(), TeamNameDialogFragment.TeamNameDialogListener {
    private var TAG = "MainActivity"

    private lateinit var bNewGame: MaterialButton
    private lateinit var bLoadGame: MaterialButton
    private lateinit var bSettings: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bNewGame = findViewById(R.id.mb_newgame)
        bLoadGame = findViewById(R.id.mb_loadgame)
        bSettings = findViewById(R.id.mb_settings)

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

    private fun showTeamNameDialog(){
        val dialog = TeamNameDialogFragment()
        dialog.show(supportFragmentManager, "TeamNameDialogFragment")

        Log.d(TAG, "Showing team name dialog.")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        val team1 = dialog.dialog?.findViewById<EditText>(R.id.et_dialog_name_team1)?.text.toString()
        val team2 = dialog.dialog?.findViewById<EditText>(R.id.et_dialog_name_team2)?.text.toString()

        Toast.makeText(this, "$team1 $team2", Toast.LENGTH_SHORT).show()

        Log.d(TAG, "Save team names clicked.")
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dismiss()

        Log.d(TAG, "Dialog dismissed.")
    }
}