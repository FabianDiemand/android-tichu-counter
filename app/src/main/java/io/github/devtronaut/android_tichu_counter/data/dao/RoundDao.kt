package io.github.devtronaut.android_tichu_counter.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import kotlinx.coroutines.flow.Flow

/**
 * Dao for the Rounds table.
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
@Dao
abstract class RoundDao {
    @Query("SELECT * FROM rounds WHERE fk_game_id LIKE :gameId ORDER BY round_index DESC")
    abstract fun getRoundsOfGame(gameId: Long): Flow<List<Round>>

    @Insert
    abstract suspend fun insertOne(round: Round)

    @Delete
    abstract suspend fun deleteOne(round: Round)
}