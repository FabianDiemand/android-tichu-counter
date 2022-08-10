package io.github.devtronaut.android_tichu_counter.data.entities.helper

import androidx.room.Embedded
import androidx.room.Relation
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round


data class GameWithRounds(
    @Embedded val game: Game,

    @Relation(
        parentColumn = "game_id",
        entityColumn = "fk_game_id"
    )
    val rounds: List<Round>
)