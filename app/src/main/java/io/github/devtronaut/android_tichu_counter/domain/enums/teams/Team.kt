package io.github.devtronaut.android_tichu_counter.domain.enums.teams

enum class Team {
    FIRST_TEAM {
        override fun getOpponent() = SECOND_TEAM
    },
    SECOND_TEAM {
        override fun getOpponent() = FIRST_TEAM
    };

    abstract fun getOpponent(): Team
}