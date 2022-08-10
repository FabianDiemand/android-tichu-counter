package io.github.devtronaut.android_tichu_counter.data.repository

import io.github.devtronaut.android_tichu_counter.data.dao.RoundDao
import io.github.devtronaut.android_tichu_counter.data.entities.Round

/**
 * Repository class for Round related database requests.
 *
 * Copyright (C) 2022  Devtronaut
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Find a copy of the GNU GPL in the root-level file "LICENCE".
 */
class RoundRepository(private val roundDao: RoundDao) {
    suspend fun deleteOne(round: Round){
        roundDao.deleteOne(round)
    }

    suspend fun insertOne(round: Round){
        roundDao.insertOne(round)
    }
}