package com.application.android_tichu_counter.data.repository

import com.application.android_tichu_counter.data.dao.GameDao
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds
import kotlinx.coroutines.flow.Flow
import java.util.*

class GameRepository(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAll()
    }

    fun getById(gameId: String): Flow<Game>{
        return gameDao.getById(gameId)
    }

    fun getGameWithRounds(gameId: String): Flow<GameWithRounds> {
        return gameDao.getWithRoundsById(gameId)
    }

    suspend fun insertMany(games: List<Game>){
        gameDao.insertMany(games)
    }

    suspend fun insertOne(game: Game){
        return gameDao.insertOne(game)
    }

    suspend fun updateOne(game: Game){
        game.updatedAt = Date()
        gameDao.updateGame(game)
    }

    suspend fun deleteOne(game: Game){
        gameDao.deleteOne(game)
    }

    fun deleteAll(){
        gameDao.deleteAll()
    }
}