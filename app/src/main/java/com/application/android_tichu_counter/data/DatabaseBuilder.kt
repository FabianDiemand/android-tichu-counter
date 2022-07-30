package com.application.android_tichu_counter.data

import android.content.Context
import androidx.room.Room
import com.application.android_tichu_counter.data.converters.date.DateConverter

object DatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "tichu_app_database"
        )
            .addTypeConverter(DateConverter())
            .build()
}