package io.github.devtronaut.android_tichu_counter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.devtronaut.android_tichu_counter.app.TichuApplication
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.viewmodel.GameViewModel
import io.github.devtronaut.android_tichu_counter.databinding.ActivityLoadGameBinding
import io.github.devtronaut.android_tichu_counter.ui.adapter.GameClickDeleteInterface
import io.github.devtronaut.android_tichu_counter.ui.adapter.GameClickInterface
import io.github.devtronaut.android_tichu_counter.ui.adapter.GamesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoadGameActivity : BaseActivity(), GameClickInterface, GameClickDeleteInterface {

    private lateinit var binding: ActivityLoadGameBinding

    @Inject
    lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as TichuApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityLoadGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gamesAdapter = GamesAdapter(this, this, this)
        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gamesAdapter

        observeGames(gamesAdapter)
        setListeners()
    }

    private fun setListeners() {
        binding.ibBackbutton.setOnClickListener {
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

                binding.tvNoGame.visibility = if (it.isEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                adapter.updateList(it)
            }
        }
    }
}