package com.application.android_tichu_counter.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.application.android_tichu_counter.data.AppDatabase
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds
import com.application.android_tichu_counter.data.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {
    val allGames: LiveData<List<Game>>
    private val repository: GameRepository

    init {
        val dao = AppDatabase.getInstance(application).gameDao()
        repository = GameRepository(dao)
        allGames = repository.getAllGames()
    }

    fun getGameWithRounds(gameId: Int): GameWithRounds{
        return repository.getGameWithRounds(gameId)
    }

    fun addGame(game: Game) = viewModelScope.launch(Dispatchers.IO){
        repository.insertOne(game)
    }

    fun addGames(games: List<Game>) = viewModelScope.launch(Dispatchers.IO){
        repository.insertMany(games)
    }

    fun deleteGame(game: Game) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteOne(game)
    }

    fun deleteAllGames() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }


}