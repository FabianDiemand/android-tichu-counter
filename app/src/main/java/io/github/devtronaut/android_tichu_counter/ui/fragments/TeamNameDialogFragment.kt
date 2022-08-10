package io.github.devtronaut.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import io.github.devtronaut.android_tichu_counter.databinding.FragmentTeamNameDialogBinding
import io.github.devtronaut.android_tichu_counter.ui.activities.BaseActivity

/**
 * Fragment to allow users to enter their teams names before starting the game.
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
class TeamNameDialogFragment(val context: BaseActivity) : DialogFragment() {
    companion object {
        private var TAG = "TeamNameDialogFragment"
    }

    private lateinit var listener: TeamNameDialogListener
    private var _binding: FragmentTeamNameDialogBinding? = null

    private val binding get() = _binding!!

    interface TeamNameDialogListener {
        fun onDialogSaveClicked(teamName1: String, teamName2: String)
    }

    fun showDialog(tag: String) {
        super.show(context.supportFragmentManager, tag)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "Dialog attached.")

        try {
            listener = context as TeamNameDialogListener
            Log.d(TAG, "Listener instantiated.")
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement TeamNameDialogListener."))
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentTeamNameDialogBinding.inflate(LayoutInflater.from(context))

        setOnClickListeners()

        Log.d(TAG, "Create dialog.")

        return activity?.let {
            AlertDialog.Builder(it)
                .setView(binding.root)
                .setCancelable(false)
                .create()
        } ?: throw IllegalStateException("Activity cannot be null!")
    }

    private fun setOnClickListeners() {
        binding.ibBackbutton.setOnClickListener {
            dismiss()
        }

        binding.bSaveTeamnames.setOnClickListener {
            val team1 = binding.etDialogNameTeam1.text.toString()
            val team2 = binding.etDialogNameTeam2.text.toString()
            listener.onDialogSaveClicked(team1, team2)

            dismiss()
        }
    }
}