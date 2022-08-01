package com.application.android_tichu_counter.data.repository

import androidx.lifecycle.LiveData
import com.application.android_tichu_counter.data.dao.GameDao
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds
import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAll()
    }

    fun getById(gameId: Long): Flow<Game>{
        return gameDao.getById(gameId)
    }

    fun getGameWithRounds(gameId: Long): Flow<GameWithRounds> {
        return gameDao.getWithRoundsById(gameId)
    }

    suspend fun insertMany(games: List<Game>){
        gameDao.insertMany(games)
    }

    suspend fun insertOne(game: Game){
        gameDao.insertOne(game)
    }

    suspend fun updateOne(game: Game){
        gameDao.updateGame(game)
    }

    suspend fun deleteOne(game: Game){
        gameDao.deleteOne(game)
    }

    fun deleteAll(){
        gameDao.deleteAll()
    }
}