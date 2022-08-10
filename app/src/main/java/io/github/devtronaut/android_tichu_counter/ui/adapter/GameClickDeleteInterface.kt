package io.github.devtronaut.android_tichu_counter.ui.adapter

import io.github.devtronaut.android_tichu_counter.data.entities.Game

interface GameClickDeleteInterface {
    fun onDeleteIconClick(game: Game, position: Int)
}