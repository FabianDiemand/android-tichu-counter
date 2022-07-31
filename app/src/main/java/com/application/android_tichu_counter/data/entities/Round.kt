package com.application.android_tichu_counter.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "round_id")
    val roundId: Long,

    @ColumnInfo(name = "fk_game_id")
    var gameId: Long,

    @ColumnInfo(name = "first_team_tichu_success")
    val firstTeamTichuSuccess: Boolean?,

    @ColumnInfo(name = "second_team_tichu_success")
    val secondTeamTichuSuccess: Boolean?,

    @ColumnInfo(name = "first_team_grandtichu_success")
    val firstTeamGrandtichuSuccess: Boolean?,

    @ColumnInfo(name = "second_team_grandtichu_success")
    val secondTeamGrandtichuSuccess: Boolean?,

    @ColumnInfo(name = "first_team_double_win")
    val firstTeamDoubleWin: Boolean,

    @ColumnInfo(name = "second_team_double_win")
    val secondTeamDoubleWin: Boolean,

    @ColumnInfo(name = "first_team_round_score")
    var firstTeamRoundScore: Int,

    @ColumnInfo(name = "second_team_round_score")
    val secondTeamRoundScore: Int
)