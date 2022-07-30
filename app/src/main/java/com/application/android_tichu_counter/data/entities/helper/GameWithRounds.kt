package com.application.android_tichu_counter.data.entities.helper

import androidx.room.Embedded
import androidx.room.Relation
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round

data class GameWithRounds(
    @Embedded val game: Game,

    @Relation(
        parentColumn = "game_id",
        entityColumn = "round_d"
    )
    val rounds: List<Round>
)