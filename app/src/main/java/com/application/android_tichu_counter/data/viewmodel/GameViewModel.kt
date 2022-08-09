package com.application.android_tichu_counter.data.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.android_tichu_counter.app.TichuApplication
import com.application.android_tichu_counter.data.TichuDatabase
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds
import com.application.android_tichu_counter.data.repository.GameRepository
import com.application.android_tichu_counter.domain.dagger.modules.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

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