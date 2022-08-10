package io.github.devtronaut.android_tichu_counter.data.repository

import io.github.devtronaut.android_tichu_counter.data.dao.GameDao
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.helper.GameWithRounds
import kotlinx.coroutines.flow.Flow
import java.util.*

/**
 * Repository class for Game related database requests.
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
class GameRepository(private val gameDao: GameDao) {

    fun getAllGames(): Flow<List<Game>> {
        return gameDao.getAll()
    }

    fun getGameWithRounds(gameId: String): Flow<GameWithRounds> {
        return gameDao.getWithRoundsById(gameId)
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
}