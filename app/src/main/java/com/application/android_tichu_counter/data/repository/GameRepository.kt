package com.application.android_tichu_counter.data.repository

import androidx.lifecycle.LiveData
import com.application.android_tichu_counter.data.dao.GameDao
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds

class GameRepository(private val gameDao: GameDao) {
    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAll()
    }

    fun getGameWithRounds(gameId: Int): GameWithRounds{
        return gameDao.getWithRoundsById(gameId)
    }

    suspend fun insertMany(games: List<Game>){
        gameDao.insertMany(games)
    }

    suspend fun insertOne(game: Game){
        gameDao.insertOne(game)
    }

    suspend fun deleteOne(game: Game){
        gameDao.deleteOne(game)
    }

    fun deleteAll(){
        gameDao.deleteAll()
    }
}