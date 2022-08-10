package io.github.devtronaut.android_tichu_counter.data.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.github.devtronaut.android_tichu_counter.app.TichuApplication
import io.github.devtronaut.android_tichu_counter.data.TichuDatabase
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import io.github.devtronaut.android_tichu_counter.data.entities.helper.GameWithRounds
import io.github.devtronaut.android_tichu_counter.data.repository.GameRepository
import io.github.devtronaut.android_tichu_counter.domain.dagger.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Game related business logic.
 *
 * Copyright (C) 2022  Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Find a copy of the GNU GPL in the root-level file "LICENCE".
 */
class GameViewModel @Inject constructor(
    application: TichuApplication,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :
    AndroidViewModel(application) {
    val allGames: Flow<List<Game>>
    private val repository: GameRepository

    init {
        val dao = TichuDatabase.getInstance(application).gameDao()
        repository = GameRepository(dao)
        allGames = repository.getAllGames()
    }

    fun getGameWithRounds(gameId: String): Flow<GameWithRounds> {
        return repository.getGameWithRounds(gameId)
    }

    fun addGame(game: Game) = viewModelScope.launch(ioDispatcher) {
        repository.insertOne(game)
    }

    fun updateGame(game: Game) = viewModelScope.launch(ioDispatcher) {
        repository.updateOne(game)
    }

    fun deleteGame(game: Game) = viewModelScope.launch(ioDispatcher) {
        repository.deleteOne(game)
    }

    fun removeRoundFromGame(game: Game, round: Round) {
        game.firstTeamScore -= round.firstTeamRoundScore
        game.secondTeamScore -= round.secondTeamRoundScore
        updateGame(game)
    }
}