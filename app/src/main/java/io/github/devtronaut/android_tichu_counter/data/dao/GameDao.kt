package io.github.devtronaut.android_tichu_counter.data.dao

import androidx.room.*
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import io.github.devtronaut.android_tichu_counter.data.entities.helper.GameWithRounds
import kotlinx.coroutines.flow.Flow

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