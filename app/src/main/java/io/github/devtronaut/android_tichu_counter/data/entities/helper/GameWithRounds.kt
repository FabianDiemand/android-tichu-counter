package io.github.devtronaut.android_tichu_counter.data.entities.helper

import androidx.room.Embedded
import androidx.room.Relation
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round

/**
 * Data Class to model the one-to-many relation of Game and Round.
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
data class GameWithRounds(
    @Embedded val game: Game,

    @Relation(
        parentColumn = "game_id",
        entityColumn = "fk_game_id"
    )
    val rounds: List<Round>
)