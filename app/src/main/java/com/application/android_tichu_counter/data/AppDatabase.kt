package com.application.android_tichu_counter.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.application.android_tichu_counter.data.converters.date.DateConverter
import com.application.android_tichu_counter.data.dao.GameDao
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round

@Database(
    entities = [Game::class, Round::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
