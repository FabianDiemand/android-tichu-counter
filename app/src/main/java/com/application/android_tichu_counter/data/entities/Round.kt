package com.application.android_tichu_counter.data.entities

import androidx.room.*
import java.util.*

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
    var roundId: Long,

    @ColumnInfo(name = "fk_game_id")
    var gameId: Long,

    @ColumnInfo(name = "round_index")
    var roundIndex: Int,

    @ColumnInfo(name = "first_team_tichu_success")
    var firstTeamTichu: Boolean?,

    @ColumnInfo(name = "second_team_tichu_success")
    var secondTeamTichu: Boolean?,

    @ColumnInfo(name = "first_team_grandtichu_success")
    var firstTeamGrandtichu: Boolean?,

    @ColumnInfo(name = "second_team_grandtichu_success")
    var secondTeamGrandtichu: Boolean?,

    @ColumnInfo(name = "first_team_double_win")
    var firstTeamDoubleWin: Boolean,

    @ColumnInfo(name = "second_team_double_win")
    var secondTeamDoubleWin: Boolean,

    @ColumnInfo(name = "first_team_round_score")
    var firstTeamRoundScore: Int,

    @ColumnInfo(name = "second_team_round_score")
    var secondTeamRoundScore: Int
){
    @Ignore
    constructor(fkGame: Long, roundIndex: Int):this(0, fkGame, roundIndex,null, null, null, null, false, false, -1, -1)

    fun changeFirstTeamTichu(){
        firstTeamGrandtichu = null

        firstTeamTichu = rotateBool(firstTeamTichu)
    }

    fun changeSecondTeamTichu(){
        secondTeamGrandtichu = null

        secondTeamTichu = rotateBool(secondTeamTichu)
    }

    fun changeFirstTeamGrandtichu(){
        firstTeamTichu = null

        firstTeamGrandtichu = rotateBool(firstTeamGrandtichu)
    }

    fun changeSecondTeamGrandtichu(){
        secondTeamTichu = null

        secondTeamGrandtichu = rotateBool(secondTeamGrandtichu)
    }

    fun setFirstTeamDoubleWin(){
        firstTeamDoubleWin = true
        calculateFirstTeamScore(0)
        calculateSecondTeamScore(0)
    }

    fun setSecondTeamDoubleWin(){
        secondTeamDoubleWin = true
        calculateSecondTeamScore(0)
        calculateFirstTeamScore(0)
    }

    fun calculateFirstTeamScore(roundPoints: Int): Int{
        var v = if(firstTeamDoubleWin){
            200
        } else {
            roundPoints
        }

        if(firstTeamTichu == true){
            v += 100
        } else if(firstTeamTichu == false){
            v -= 100
        }

        if(firstTeamGrandtichu == true){
            v += 200
        } else if(firstTeamGrandtichu == false){
            v -= 200
        }

        firstTeamRoundScore = v
        return firstTeamRoundScore
    }

    fun calculateSecondTeamScore(roundPoints: Int): Int{
        var v = if(secondTeamDoubleWin){
            200
        } else {
            roundPoints
        }

        if(secondTeamTichu == true){
            v += 100
        } else if(secondTeamTichu == false){
            v -= 100
        }

        if(secondTeamGrandtichu == true){
            v += 200
        } else if(secondTeamGrandtichu == false){
            v -= 200
        }

        secondTeamRoundScore = v
        return secondTeamRoundScore
    }

    private fun rotateBool(bool: Boolean?): Boolean?{
        return when(bool){
            null -> true
            true -> false
            false -> null
        }
    }
}