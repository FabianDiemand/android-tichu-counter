package io.github.devtronaut.android_tichu_counter.domain.enums.tichu_states

import io.github.devtronaut.android_tichu_counter.R

/**
 * Enum class to model all states a (Grand) Tichu might have and their
 * influence on the score as well as the states transitions.
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
enum class TichuState(val state: Boolean?) {
    NA(null) {
        override fun getColor() = R.color.yellow
        override fun getNormalScore() = 0
        override fun getGrandScore() = 0
        override fun nextState() = SUCCESS
    },
    SUCCESS(true) {
        override fun getColor() = R.color.green
        override fun getNormalScore() = 100
        override fun getGrandScore() = 200
        override fun nextState() = FAILURE

    },
    FAILURE(false) {
        override fun getColor() = R.color.red
        override fun getNormalScore() = -100
        override fun getGrandScore() = -200
        override fun nextState() = NA
    };

    abstract fun getColor(): Int
    abstract fun getNormalScore(): Int
    abstract fun getGrandScore(): Int
    abstract fun nextState(): TichuState
}