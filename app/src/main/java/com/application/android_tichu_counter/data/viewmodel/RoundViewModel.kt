package com.application.android_tichu_counter.data.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.application.android_tichu_counter.app.TichuApplication
import com.application.android_tichu_counter.data.TichuDatabase
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.repository.RoundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoundViewModel @Inject constructor(application: TichuApplication) :
    AndroidViewModel(application) {
    private val repository: RoundRepository

    init {
        val dao = TichuDatabase.getInstance(application).roundDao()
        repository = RoundRepository(dao)
    }

    fun addRound(round: Round) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertOne(round)
    }

    fun deleteRound(round: Round) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteOne(round)
    }
}