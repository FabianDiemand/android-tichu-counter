package com.application.android_tichu_counter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds

interface GameDao {
    @Query("SELECT * FROM games")
    fun getAll(): LiveData<List<Game>>

    @Transaction
    @Query("SELECT * FROM games WHERE game_id LIKE :gameId")
    fun getWithRoundsById(gameId: Int): GameWithRounds

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(game: Game)

    @Delete
    suspend fun deleteOne(game: Game)

    @Query("DELETE FROM games")
    fun deleteAll() {
    }
}