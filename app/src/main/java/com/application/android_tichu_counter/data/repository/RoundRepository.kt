package com.application.android_tichu_counter.data.repository

import com.application.android_tichu_counter.data.dao.RoundDao
import com.application.android_tichu_counter.data.entities.Round
import kotlinx.coroutines.flow.Flow

class RoundRepository(private val roundDao: RoundDao) {

    fun getRoundsOfGame(gameId: Long): Flow<List<Round>> {
        return roundDao.getRoundsOfGame(gameId)
    }

    // Delete Round
    suspend fun deleteOne(round: Round){
        roundDao.deleteOne(round)
    }

    // Insert Round
    suspend fun insertOne(round: Round){
        roundDao.insertOne(round)
    }
}