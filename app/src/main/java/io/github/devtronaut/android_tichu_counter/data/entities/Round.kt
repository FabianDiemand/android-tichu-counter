package io.github.devtronaut.android_tichu_counter.data.entities

import android.os.Parcelable
import androidx.room.*
import io.github.devtronaut.android_tichu_counter.domain.enums.teams.Team
import io.github.devtronaut.android_tichu_counter.domain.enums.teams.Team.FIRST_TEAM
import io.github.devtronaut.android_tichu_counter.domain.enums.teams.Team.SECOND_TEAM
import io.github.devtronaut.android_tichu_counter.domain.enums.tichu_states.TichuState
import io.github.devtronaut.android_tichu_counter.domain.enums.tichu_states.TichuState.*
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * Parcelable data class to model a Round entity.
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
@Parcelize
@Entity(
    tableName = "rounds",
    foreignKeys = [ForeignKey(
        entity = Game::class,
        childColumns = ["fk_game_id"],
        parentColumns = ["game_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Round(
    @PrimaryKey
    @ColumnInfo(name = "round_id", index = true)
    var roundId: String,

    @ColumnInfo(name = "fk_game_id")
    var fkGameId: String,

    @ColumnInfo(name = "round_index")
    var roundIndex: Int,

    @ColumnInfo(name = "first_team_tichu_success")
    var firstTeamTichu: TichuState,

    @ColumnInfo(name = "second_team_tichu_success")
    var secondTeamTichu: TichuState,

    @ColumnInfo(name = "first_team_grandtichu_success")
    var firstTeamGrandtichu: TichuState,

    @ColumnInfo(name = "second_team_grandtichu_success")
    var secondTeamGrandtichu: TichuState,

    @ColumnInfo(name = "first_team_double_win")
    var firstTeamDoubleWin: Boolean,

    @ColumnInfo(name = "second_team_double_win")
    var secondTeamDoubleWin: Boolean,

    @ColumnInfo(name = "first_team_round_score")
    var firstTeamRoundScore: Int,

    @ColumnInfo(name = "second_team_round_score")
    var secondTeamRoundScore: Int
) : Parcelable {
    @Ignore
    constructor(fkGameId: String, roundIndex: Int) :
            this(
                UUID.randomUUID().toString(), fkGameId, roundIndex,
                NA, NA, NA, NA,
                false, false,
                0, 0
            )

    @Ignore
    constructor(
        fkGameId: String,
        roundIndex: Int,
        firstTeamTichu: TichuState,
        secondTeamTichu: TichuState,
        firstTeamGrandtichu: TichuState,
        secondTeamGrandtichu: TichuState,
        firstTeamDoubleWin: Boolean,
        secondTeamDoubleWin: Boolean,
        firstTeamRoundScore: Int,
        secondTeamRoundScore: Int
    ) :
            this(
                UUID.randomUUID().toString(),
                fkGameId,
                roundIndex,
                firstTeamTichu,
                secondTeamTichu,
                firstTeamGrandtichu,
                secondTeamGrandtichu,
                firstTeamDoubleWin,
                secondTeamDoubleWin,
                firstTeamRoundScore,
                secondTeamRoundScore
            )

    /**
     * Calculate the roundscore entered by a team.
     *
     * @param team the team for whom the score was entered
     * @param roundPoints the score that was entered
     */
    fun calculateScoreByTeam(team: Team, roundPoints: Int) {
        if (team == FIRST_TEAM) {
            calculateFirstTeamScore(roundPoints)
            // use the difference of 100 the the scored points for the opponent team
            calculateSecondTeamScore(100 - roundPoints)
        } else if (team == SECOND_TEAM) {
            calculateSecondTeamScore(roundPoints)
            // use the difference of 100 the the scored points for the opponent team
            calculateFirstTeamScore(100 - roundPoints)
        }
    }

    /**
     * Check if the round is in a valid state and therefore can be finished.
     *
     * @return true if the round is valid, false otherwise
     */
    fun isValidState(): Boolean {
        if (firstTeamTichu == SUCCESS && secondTeamTichu == SUCCESS) {
            return false
        } else if (firstTeamTichu == SUCCESS && secondTeamGrandtichu == SUCCESS) {
            return false
        } else if (firstTeamGrandtichu == SUCCESS && secondTeamTichu == SUCCESS) {
            return false
        } else if (firstTeamGrandtichu == SUCCESS && secondTeamGrandtichu == SUCCESS) {
            return false
        }

        return true
    }

    /**
     * Checks if a double win is possible for the passed team.
     *
     * @param team the team for whom to check the double win
     * @return true if a double win is possible for the team, false otherwise
     */
    fun isDoubleWinPossibleForTeam(team: Team): Boolean {
        if (team == FIRST_TEAM) {
            return secondTeamTichu != SUCCESS
                    && secondTeamGrandtichu != SUCCESS
                    && firstTeamTichu != FAILURE
                    && firstTeamGrandtichu != FAILURE
        }

        return firstTeamTichu != SUCCESS
                && firstTeamGrandtichu != SUCCESS
                && secondTeamTichu != FAILURE
                && secondTeamGrandtichu != FAILURE
    }

    /**
     * Update the tichu state of the team.
     *
     * @param team whose tichu state to update
     */
    fun changeTichuForTeam(team: Team) {
        if (team == FIRST_TEAM) {
            firstTeamTichu = firstTeamTichu.nextState()
            firstTeamGrandtichu = NA
        } else if (team == SECOND_TEAM) {
            secondTeamTichu = secondTeamTichu.nextState()
            secondTeamGrandtichu = NA
        }
    }

    /**
     * Update the grand tichu state of the team
     *
     * @param team whose grand tichu state to update
     */
    fun changeGrandTichuForTeam(team: Team) {
        if (team == FIRST_TEAM) {
            firstTeamGrandtichu = firstTeamGrandtichu.nextState()
            firstTeamTichu = NA
        } else if (team == SECOND_TEAM) {
            secondTeamGrandtichu = secondTeamGrandtichu.nextState()
            secondTeamTichu = NA
        }
    }

    /**
     * Set the double win for a team and unset the double win for the opponent.
     *
     * @param team whose double win to set
     */
    fun setDoubleWinForTeam(team: Team) {
        if (team == FIRST_TEAM) {
            firstTeamDoubleWin = true
            secondTeamDoubleWin = false
        } else if (team == SECOND_TEAM) {
            secondTeamDoubleWin = true
            firstTeamDoubleWin = false
        }
    }

    private fun calculateFirstTeamScore(roundPoints: Int) {
        var v = if (firstTeamDoubleWin) {
            200
        } else if (secondTeamDoubleWin) {
            0
        } else {
            roundPoints
        }

        v += firstTeamTichu.getNormalScore()
        v += firstTeamGrandtichu.getGrandScore()

        firstTeamRoundScore = v
    }

    private fun calculateSecondTeamScore(roundPoints: Int) {
        var v = if (secondTeamDoubleWin) {
            200
        } else if (firstTeamDoubleWin) {
            0
        } else {
            roundPoints
        }

        v += secondTeamTichu.getNormalScore()
        v += secondTeamGrandtichu.getGrandScore()

        secondTeamRoundScore = v
    }
}

/**
 * Used as a helper to pass a more compact representation of a round
 * to the RoundProgressFragment.
 *
 * @author Devtronaut
 */
@Parcelize
class TeamRound(
    val tichu: TichuState,
    val grandTichu: TichuState,
    val doubleWin: Boolean,
    val roundScore: Int
) : Parcelable