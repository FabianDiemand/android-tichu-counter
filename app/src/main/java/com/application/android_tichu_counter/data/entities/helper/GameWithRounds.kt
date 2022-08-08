package com.application.android_tichu_counter.data.entities.helper

import androidx.room.Embedded
import androidx.room.Relation
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import kotlinx.coroutines.flow.Flow

data class GameWithRounds(
    @Embedded val game: Game,

    @Relation(
        parentColumn = "game_id",
        entityColumn = "fk_game_id"
    )
    val rounds: List<Round>
)