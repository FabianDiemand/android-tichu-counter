package com.application.android_tichu_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

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
    }

    private fun setOnClickListeners(){
        bNewGame.setOnClickListener {
            startGame(it)
        }

        bLoadGame.setOnClickListener {
            loadGame(it)
        }

        bNewGame.setOnClickListener {
            startSettings(it)
        }
    }

    private fun startGame(view: View){

    }

    private fun loadGame(view: View){

    }

    private fun startSettings(view: View){

    }

}