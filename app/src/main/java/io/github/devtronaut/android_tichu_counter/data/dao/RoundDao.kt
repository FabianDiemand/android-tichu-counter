package io.github.devtronaut.android_tichu_counter.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RoundDao {
    @Query("SELECT * FROM rounds WHERE fk_game_id LIKE :gameId ORDER BY round_index DESC")
    abstract fun getRoundsOfGame(gameId: Long): Flow<List<Round>>

    @Insert
    abstract suspend fun insertOne(round: Round)

    @Delete
    abstract suspend fun deleteOne(round: Round)
}