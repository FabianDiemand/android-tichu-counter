package com.application.android_tichu_counter.data.entities

import android.provider.Settings.Global.getString
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.application.android_tichu_counter.R
import java.time.LocalDateTime

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val gameId: Int,

    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,

    @ColumnInfo(name = "is_finished")
    val isFinished: Boolean,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "first_team")
    val firstTeam: String,

    @ColumnInfo(name = "first_team_score")
    val firstTeamScore: Int,

    @ColumnInfo(name = "second_team")
    val secondTeam: String,

    @ColumnInfo(name = "second_team_score")
    val secondTeamScore: Int,
){
    fun isFinished(): Int{
        if(isFinished){
            return R.string.finished
        }

        return R.string.open
    }
}