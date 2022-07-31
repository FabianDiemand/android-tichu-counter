package com.application.android_tichu_counter.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.viewmodel.GameViewModel
import com.application.android_tichu_counter.ui.adapter.GameClickDeleteInterface
import com.application.android_tichu_counter.ui.adapter.GameClickInterface
import com.application.android_tichu_counter.ui.adapter.GamesAdapter
import kotlinx.coroutines.launch

class LoadGameActivity : AppCompatActivity(), GameClickInterface, GameClickDeleteInterface {

    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameViewModel::class.java]
    }

    private lateinit var rvGames: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game)

        rvGames = findViewById(R.id.rv_games)

        rvGames.layoutManager = LinearLayoutManager(this)

        val gamesAdapter = GamesAdapter(this, this, this)
        rvGames.adapter = gamesAdapter

        observeGames(gamesAdapter)
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


    private fun observeGames(adapter: GamesAdapter){
        lifecycleScope.launch {
            gameViewModel.allGames.collect {
                if (it.isNotEmpty()) {
                    adapter.updateList(it)
                }
            }
        }
    }
}