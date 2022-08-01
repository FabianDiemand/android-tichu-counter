package com.application.android_tichu_counter.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.android_tichu_counter.data.TichuDatabase
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.repository.RoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoundViewModel(application: Application): AndroidViewModel(application) {
    private val repository: RoundRepository

    init{
        val dao = TichuDatabase.getInstance(application).roundDao()
        repository = RoundRepository(dao)
    }

    fun getRoundsOfGame(gameId: Long): Flow<List<Round>> {
        return repository.getRoundsOfGame(gameId)
    }

    fun addRound(round: Round) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertOne(round)
    }

    fun deleteRound(round: Round) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteOne(round)
    }
}