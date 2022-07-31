package com.application.android_tichu_counter.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds

@Dao
abstract class GameDao {
    @Query("SELECT * FROM games")
    abstract fun getAll(): LiveData<List<Game>>

    @Transaction
    @Query("SELECT * FROM games WHERE game_id LIKE :gameId")
    abstract fun getWithRoundsById(gameId: Int): GameWithRounds

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMany(games: List<Game>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOne(game: Game): Long

    suspend fun insertWithRounds(game: GameWithRounds) {
        val id: Long = insertOne(game.game)

        game.rounds.forEach {
            it.gameId = id
        }

        insertAllRounds(game.rounds)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllRounds(rounds: List<Round>)

    @Delete
    abstract suspend fun deleteOne(game: Game)

    @Query("DELETE FROM games")
    abstract fun deleteAll()
}