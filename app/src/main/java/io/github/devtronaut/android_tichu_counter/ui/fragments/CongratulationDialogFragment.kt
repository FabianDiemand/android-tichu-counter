package io.github.devtronaut.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.github.devtronaut.android_tichu_counter.R
import io.github.devtronaut.android_tichu_counter.databinding.FragmentCongratulationBinding

/**
 * Fragment for the congratulation screen shown after one team won the game.
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
class CongratulationDialogFragment : DialogFragment() {
    companion object {
        private const val WINNER_NAME = "Winner_Name"
        private const val WINNER_SCORE = "Winner_Score"
        private const val LOSER_SCORE = "Loser_Score"

        private const val TAG = "CongratulationFragment"

        @JvmStatic
        fun getInstance(winnerName: String, winnerScore: Int, loserScore: Int) =
            CongratulationDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(WINNER_NAME, winnerName)
                    putInt(WINNER_SCORE, winnerScore)
                    putInt(LOSER_SCORE, loserScore)
                }
            }
    }

    private var _binding: FragmentCongratulationBinding? = null
    private val binding get() = _binding!!

    private var winnerName: String? = null
    private var winnerScore: Int? = null
    private var loserScore: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            winnerName = it.getString(WINNER_NAME)
            winnerScore = it.getInt(WINNER_SCORE)
            loserScore = it.getInt(LOSER_SCORE)
        }
    }

    override fun getTheme(): Int {
        return R.style.DialogStyle
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCongratulationBinding.inflate(inflater, null, false)
        val view = binding.root

        setupUi()
        setOnClickListeners()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUi() {
        binding.tvWinningTeam.text = winnerName
        binding.tvWinningScore.text = getString(R.string.scores, winnerScore, loserScore)
    }

    private fun setOnClickListeners() {
        binding.bThanks.setOnClickListener {
            dismiss()
        }
    }
}