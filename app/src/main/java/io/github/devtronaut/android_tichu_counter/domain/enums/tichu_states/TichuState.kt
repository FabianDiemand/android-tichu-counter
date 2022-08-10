package io.github.devtronaut.android_tichu_counter.domain.enums.tichu_states

import io.github.devtronaut.android_tichu_counter.R

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