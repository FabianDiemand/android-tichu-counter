package io.github.devtronaut.android_tichu_counter.data.dao

import androidx.room.*
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import io.github.devtronaut.android_tichu_counter.data.entities.helper.GameWithRounds
import kotlinx.coroutines.flow.Flow

/**
 * Dao for the Games table.
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
@Dao
abstract class GameDao {
    @Query("SELECT * FROM games ORDER BY updated_at DESC")
    abstract fun getAll(): Flow<List<Game>>

    @Query("SELECT * FROM games WHERE game_id LIKE :gameId")
    abstract fun getById(gameId: String): Flow<Game>

    @Transaction
    @Query("SELECT * FROM games WHERE game_id LIKE :gameId")
    abstract fun getWithRoundsById(gameId: String): Flow<GameWithRounds>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMany(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOne(game: Game)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllRounds(rounds: List<Round>)

    @Update
    abstract suspend fun updateGame(game: Game)

    @Delete
    abstract suspend fun deleteOne(game: Game)

    @Query("DELETE FROM games")
    abstract fun deleteAll()
}