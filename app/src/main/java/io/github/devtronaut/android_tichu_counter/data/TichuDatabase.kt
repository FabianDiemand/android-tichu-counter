package io.github.devtronaut.android_tichu_counter.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import io.github.devtronaut.android_tichu_counter.data.converters.date.DateConverter
import io.github.devtronaut.android_tichu_counter.data.dao.GameDao
import io.github.devtronaut.android_tichu_counter.data.dao.RoundDao
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Database class for the Tichu Counter application.
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
@Database(
    entities = [Game::class, Round::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class TichuDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun roundDao(): RoundDao

    companion object {
        private var INSTANCE: TichuDatabase? = null

        fun getInstance(context: Context): TichuDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TichuDatabase::class.java,
                "tichu_counter_db"
            )
                .addCallback(seedDatabaseCallback(context))
                .build()

        @OptIn(DelicateCoroutinesApi::class)
        private fun seedDatabaseCallback(context: Context): Callback {
            Log.d("AppDatabase", "Prepopulate!")

            return object : Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)

                    val gameDao = getInstance(context).gameDao()

                    GlobalScope.launch{
                        val game = GamesSeeder().meWin

                        gameDao.insertOne(game.game)

                        gameDao.insertAllRounds(game.rounds)
                    }
                }
            }
        }
    }
}

