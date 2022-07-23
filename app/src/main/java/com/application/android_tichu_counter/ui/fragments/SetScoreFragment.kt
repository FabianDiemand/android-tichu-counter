package com.application.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.application.android_tichu_counter.R
import com.google.android.material.button.MaterialButton
import com.shawnlin.numberpicker.NumberPicker

private const val TEAM_NAME = "teamname"
private const val TAG = "SetScoreFragment"

class SetScoreFragment: Fragment() {
    private var teamName: String? = null

    private lateinit var listener: SetScoreListener
    private lateinit var value: String

    interface SetScoreListener{
        fun onTichuClicked(setScoreFragment: SetScoreFragment)
        fun onGrandTichuClicked(setScoreFragment: SetScoreFragment)
        fun onDoubleWinClicked(setScoreFragment: SetScoreFragment)
        fun onOkClicked(setScoreFragment: SetScoreFragment, value: Int)
        fun onRemoveClicked(setScoreFragment: SetScoreFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try{
            listener = context as SetScoreListener
            Log.d(TAG, "Listener instantiated.")
        } catch(e: ClassCastException){
            throw ClassCastException("$context must implement SetScoreListener.")
        }

        arguments?.let {
            teamName = it.getString(TEAM_NAME)
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = LayoutInflater.from(context).inflate(R.layout.fragment_set_score, null, false)

        val bTichu = fragment.findViewById<MaterialButton>(R.id.b_tichu)
        val bGrandTichu = fragment.findViewById<MaterialButton>(R.id.b_grandtichu)
        val bDoubleWin = fragment.findViewById<MaterialButton>(R.id.b_doublewin)
        val bOk = fragment.findViewById<MaterialButton>(R.id.b_submit)
        val bRemove = fragment.findViewById<ImageButton>(R.id.b_remove)
        val npScore = fragment.findViewById<NumberPicker>(R.id.np_scorepicker)
        val bonzai = ResourcesCompat.getFont(requireContext(), R.font.bonzai)

        val valuesArray: Array<String> = resources.getStringArray(R.array.scores)

        npScore.setSelectedTypeface(bonzai)
        npScore.typeface = bonzai
        npScore.minValue = 0
        npScore.maxValue = 25
        npScore.value = 0
        npScore.displayedValues = valuesArray

        bTichu.setOnClickListener { listener.onTichuClicked(this) }
        bGrandTichu.setOnClickListener { listener.onGrandTichuClicked(this) }
        bDoubleWin.setOnClickListener { listener.onDoubleWinClicked(this) }
        bOk.setOnClickListener {
            val score = valuesArray[npScore.value].toInt()
            listener.onOkClicked(this, score)
        }
        bRemove.setOnClickListener { listener.onRemoveClicked(this) }

        return fragment
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param teamName name of the team whose score is evaluated.
         * @return A new instance of fragment SetScoreFragment.
         */
        @JvmStatic
        fun getInstance(teamName: String) =
            SetScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(TEAM_NAME, teamName)
                }
            }
    }
}