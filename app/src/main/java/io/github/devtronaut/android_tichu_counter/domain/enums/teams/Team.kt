package io.github.devtronaut.android_tichu_counter.domain.enums.teams

/**
 * Enumeration class to model two teams with their respective counterpart.
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
enum class Team {
    FIRST_TEAM {
        override fun getOpponent() = SECOND_TEAM
    },
    SECOND_TEAM {
        override fun getOpponent() = FIRST_TEAM
    };

    abstract fun getOpponent(): Team
}