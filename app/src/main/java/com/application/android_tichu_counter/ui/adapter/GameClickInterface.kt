package com.application.android_tichu_counter.ui.adapter

import com.application.android_tichu_counter.data.entities.Game

interface GameClickInterface {
    fun onGameClick(game: Game)
}