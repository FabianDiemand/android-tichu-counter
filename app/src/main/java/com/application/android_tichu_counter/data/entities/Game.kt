package com.application.android_tichu_counter.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.domain.enums.teams.Team
import com.application.android_tichu_counter.domain.enums.teams.Team.FIRST_TEAM
import com.application.android_tichu_counter.domain.enums.teams.Team.SECOND_TEAM
import java.util.*

@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    @ColumnInfo(name = "game_id")
    val gameId: String,

    @ColumnInfo(name = "updated_at")
    var updatedAt: Date,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "is_finished")
    var finished: Boolean,

    @ColumnInfo(name = "first_team")
    val firstTeam: String,

    @ColumnInfo(name = "first_team_score")
    var firstTeamScore: Int = 0,

    @ColumnInfo(name = "second_team")
    val secondTeam: String,

    @ColumnInfo(name = "second_team_score")
    var secondTeamScore: Int = 0,

    @ColumnInfo(name = "rounds_played")
    var roundsPlayed: Int = 0,

    @ColumnInfo(name = "winning_team")
    var winningTeam: Team? = null
){
    @Ignore
    constructor(firstTeamName: String, secondTeamName: String): this(UUID.randomUUID().toString(), Date(), Date(), false, firstTeamName, 0, secondTeamName, 0)

    @Ignore
    constructor(
        finished: Boolean,
        firstTeamName: String,
        secondTeamName: String,
        firstTeamScore: Int,
        secondTeamScore: Int
    ) : this(
        UUID.randomUUID().toString(),
        Date(),
        Date(),
        finished,
        firstTeamName,
        firstTeamScore,
        secondTeamName,
        secondTeamScore
    )

    fun isFinished(): Int {
        if (finished) {
            return R.string.finished
        }

        return R.string.open
    }

    fun getWinningTeamName(): String {
        return if (winningTeam == FIRST_TEAM) {
            firstTeam
        } else {
            secondTeam
        }
    }

    fun addFirstTeamScore(roundScore: Int) {
        firstTeamScore += roundScore
        evaluateWin()
    }

    fun addSecondTeamScore(roundScore: Int) {
        secondTeamScore += roundScore
        evaluateWin()
    }

    private fun evaluateWin() {
        if (firstTeamScore >= 1000 && secondTeamScore >= 1000) {
            if (firstTeamScore > secondTeamScore) {
                setFirstTeamWin()
            } else if (firstTeamScore < secondTeamScore) {
                setSecondTeamWin()
            } else {
                finished = false
            }
        } else if (firstTeamScore >= 1000) {
            setFirstTeamWin()
        } else if (secondTeamScore >= 1000) {
            setSecondTeamWin()
        } else {
            finished = false
        }
    }

    private fun setSecondTeamWin() {
        finished = true
        winningTeam = SECOND_TEAM
    }

    private fun setFirstTeamWin() {
        finished = true
        winningTeam = FIRST_TEAM
    }
}