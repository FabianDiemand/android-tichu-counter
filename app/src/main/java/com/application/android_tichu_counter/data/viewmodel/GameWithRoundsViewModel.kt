package com.application.android_tichu_counter.data.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.android_tichu_counter.TichuApplication
import com.application.android_tichu_counter.data.TichuDatabase
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.repository.GameRepository
import com.application.android_tichu_counter.data.repository.RoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameWithRoundsViewModel @Inject constructor(application: TichuApplication) :
    AndroidViewModel(application) {

    private val roundsRepository: RoundRepository
    private val gamesRepository: GameRepository

    init {
        val tichuDatabase = TichuDatabase.getInstance(application)

        val gameDao = tichuDatabase.gameDao()
        gamesRepository = GameRepository(gameDao)

        val roundDao = tichuDatabase.roundDao()
        roundsRepository = RoundRepository(roundDao)
    }

    fun getGameWithRounds(gameId: String) = gamesRepository.getGameWithRounds(gameId)

    fun addRoundForGame(round: Round, game: Game) {
        round.fkGameId = game.gameId
        round.roundIndex = ++game.roundsPlayed

        viewModelScope.launch(Dispatchers.IO) {
            roundsRepository.insertOne(round)
            gamesRepository.updateOne(game)
        }
    }
}