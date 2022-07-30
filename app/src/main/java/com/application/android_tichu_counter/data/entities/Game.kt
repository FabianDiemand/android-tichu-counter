package com.application.android_tichu_counter.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val gameId: Int,

    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "first_team")
    val firstTeam: String,

    @ColumnInfo(name = "second_team")
    val secondTeam: String,
)