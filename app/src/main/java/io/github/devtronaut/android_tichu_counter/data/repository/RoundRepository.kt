package io.github.devtronaut.android_tichu_counter.data.repository

import io.github.devtronaut.android_tichu_counter.data.dao.RoundDao
import io.github.devtronaut.android_tichu_counter.data.entities.Round

class RoundRepository(private val roundDao: RoundDao) {
    // Delete Round
    suspend fun deleteOne(round: Round){
        roundDao.deleteOne(round)
    }

    // Insert Round
    suspend fun insertOne(round: Round){
        roundDao.insertOne(round)
    }
}