package io.github.devtronaut.android_tichu_counter.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import io.github.devtronaut.android_tichu_counter.R
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.domain.date.DateUtils

/**
 * Adapter to populate the load games recycler view.
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
class GamesAdapter(
    val context: Context,
    private val gameClickDeleteInterface: GameClickDeleteInterface,
    private val gameClickInterface: GameClickInterface
) : RecyclerView.Adapter<GamesAdapter.ViewHolder>(){

    private val allGames = ArrayList<Game>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGameState: TextView = itemView.findViewById(R.id.tv_game_state)
        val tvLastPlayed: TextView = itemView.findViewById(R.id.tv_last_played)
        val tvTeams: TextView = itemView.findViewById(R.id.tv_teams)
        val tvScores: TextView = itemView.findViewById(R.id.tv_scores)
        val ibDelete: ImageButton = itemView.findViewById(R.id.ib_delete_game)
        val clCard: ConstraintLayout = itemView.findViewById(R.id.cl_card)
    }

    interface GameClickDeleteInterface {
        fun onDeleteIconClick(game: Game, position: Int)
    }

    interface GameClickInterface {
        fun onGameClick(game: Game)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.game_rv_item, parent, false
        )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game: Game = allGames[position]

        if(game.finished){
            holder.clCard.setBackgroundResource(R.drawable.shape_game_item_background_finished)
        }

        holder.tvGameState.text = context.getString(game.isFinished())
        holder.tvLastPlayed.text = DateUtils.formatDateToLocale(game.updatedAt)
        holder.tvTeams.text = context.getString(R.string.teams, game.firstTeam, game.secondTeam)
        holder.tvScores.text = context.getString(R.string.scores, game.firstTeamScore, game.secondTeamScore)

        holder.ibDelete.setOnClickListener {
            gameClickDeleteInterface.onDeleteIconClick(allGames[position], position)
        }

        holder.itemView.setOnClickListener {
            gameClickInterface.onGameClick(allGames[position])
        }
    }

    override fun getItemCount(): Int{
        return allGames.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Game>){
        allGames.clear()
        allGames.addAll(newList)
        notifyDataSetChanged()
    }
}

