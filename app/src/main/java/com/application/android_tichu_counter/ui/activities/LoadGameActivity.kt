package com.application.android_tichu_counter.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.viewmodel.GameViewModel
import com.application.android_tichu_counter.ui.adapter.GameClickDeleteInterface
import com.application.android_tichu_counter.ui.adapter.GameClickInterface
import com.application.android_tichu_counter.ui.adapter.GamesAdapter

class LoadGameActivity : AppCompatActivity(), GameClickInterface, GameClickDeleteInterface {
    lateinit var gameViewModel: GameViewModel
    lateinit var rvGames: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)

        rvGames = findViewById(R.id.rv_games)

        rvGames.layoutManager = LinearLayoutManager(this)

        val gamesAdapter = GamesAdapter(this, this, this)
        rvGames.adapter = gamesAdapter

        gameViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(GameViewModel::class.java)

        gameViewModel.allGames.observe(this, Observer { list ->
            list?.let{
                gamesAdapter.updateList(it)
            }
        })
    }

    override fun onGameClick(game: Game) {
        val intent = Intent(this@LoadGameActivity, ScoreboardActivity::class.java)
        intent.putExtra(ScoreboardActivity.GAME_ID, game.gameId)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(game: Game) {
        gameViewModel.deleteGame(game)
    }
}