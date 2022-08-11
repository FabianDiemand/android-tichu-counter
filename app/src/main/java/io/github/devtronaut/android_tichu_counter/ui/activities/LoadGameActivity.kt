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
import io.github.devtronaut.android_tichu_counter.ui.adapter.GamesAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Activity for the LoadGame screen.
 * Extends BaseActivity to be affine to in-app language changes.
 *
 * Copyright (C) 2022 Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * Find a copy of the GNU AGPLv3 in the root-level file "LICENCE".
 */
class LoadGameActivity : BaseActivity(), GamesAdapter.GameClickInterface,
    GamesAdapter.GameClickDeleteInterface {

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