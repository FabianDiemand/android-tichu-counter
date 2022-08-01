package com.application.android_tichu_counter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.android_tichu_counter.BaseActivity
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.viewmodel.GameViewModel
import com.application.android_tichu_counter.ui.adapter.GameClickDeleteInterface
import com.application.android_tichu_counter.ui.adapter.GameClickInterface
import com.application.android_tichu_counter.ui.adapter.GamesAdapter
import kotlinx.coroutines.launch

class LoadGameActivity : BaseActivity(), GameClickInterface, GameClickDeleteInterface {

    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameViewModel::class.java]
    }

    private lateinit var rvGames: RecyclerView
    private lateinit var ibBackbutton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)

        rvGames = findViewById(R.id.rv_games)

        rvGames.layoutManager = LinearLayoutManager(this)

        val gamesAdapter = GamesAdapter(this, this, this)
        rvGames.adapter = gamesAdapter

        observeGames(gamesAdapter)

        ibBackbutton = findViewById(R.id.ib_backbutton)
        setListeners()
    }

    private fun setListeners() {
        ibBackbutton.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onGameClick(game: Game) {
        val intent = Intent(this@LoadGameActivity, ScoreboardActivity::class.java)
        intent.putExtra(ScoreboardActivity.KEY_GAME_ID, game.gameId)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(game: Game, position: Int) {
        gameViewModel.deleteGame(game)
    }

    private fun observeGames(adapter: GamesAdapter) {
        lifecycleScope.launch {
            gameViewModel.allGames.collect {
                if(it.isEmpty()){
                    findViewById<TextView>(R.id.tv_no_game).visibility = View.VISIBLE
                } else {
                    findViewById<TextView>(R.id.tv_no_game).visibility = View.GONE
                }

                adapter.updateList(it)
            }
        }
    }
}