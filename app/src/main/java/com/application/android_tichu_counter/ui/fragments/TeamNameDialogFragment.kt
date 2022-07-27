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
import com.google.android.material.button.MaterialButton

class TeamNameDialogFragment : DialogFragment() {
    companion object {
        private var TAG = "TeamNameDialogFragment"
    }

    private lateinit var listener: TeamNameDialogListener

    interface TeamNameDialogListener{
        fun onDialogSaveClicked(dialog: DialogFragment)
        fun onDialogBackClicked(dialog: DialogFragment)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        Log.d(TAG, "Dialog attached.")

        try{
            listener = context as TeamNameDialogListener
            Log.d(TAG, "Listener instantiated.")
        } catch(e: ClassCastException){
            throw ClassCastException(("$context must implement TeamNameDialogListener."))
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "Create dialog.")

        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_team_name_dialog, null, false)

            val bCancel = dialogView.findViewById<ImageButton>(R.id.ib_backbutton)
            val bSave = dialogView.findViewById<MaterialButton>(R.id.b_save_teamnames)

            bCancel.setOnClickListener {
                listener.onDialogBackClicked(this)
            }

            bSave.setOnClickListener {
                listener.onDialogSaveClicked(this)
            }

            builder.setView(dialogView)
            builder.setCancelable(false)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null!")
    }
}