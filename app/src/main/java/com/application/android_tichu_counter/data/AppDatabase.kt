package com.application.android_tichu_counter.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.application.android_tichu_counter.data.converters.date.DateConverter
import com.application.android_tichu_counter.data.dao.GameDao
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [Game::class, Round::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
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
                        gameDao.insertWithRounds(GamesSeeder().meWin)
                    }
                }
            }
        }
    }
}

