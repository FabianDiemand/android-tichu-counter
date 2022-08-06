package com.application.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.databinding.FragmentTeamNameDialogBinding
import com.google.android.material.button.MaterialButton

class TeamNameDialogFragment : DialogFragment() {
    companion object {
        private var TAG = "TeamNameDialogFragment"
    }

    private lateinit var listener: TeamNameDialogListener
    private var _binding: FragmentTeamNameDialogBinding? = null

    private val binding get() = _binding!!

    interface TeamNameDialogListener {
        fun onDialogSaveClicked(dialog: DialogFragment)
        fun onDialogBackClicked(dialog: DialogFragment)
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
            listener.onDialogBackClicked(this)
        }

        binding.bSaveTeamnames.setOnClickListener {
            listener.onDialogSaveClicked(this)
        }
    }
}