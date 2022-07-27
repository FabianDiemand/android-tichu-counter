package com.application.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.application.android_tichu_counter.R
import com.google.android.material.button.MaterialButton
import com.shawnlin.numberpicker.NumberPicker

/**
 * Fragment to set tichus and the score
 */
class SetScoreFragment: Fragment() {
    companion object {
        private const val TEAM_NAME = "teamname"
        private const val OPP_TEAM_NAME = "oppteamname"

        private const val TAG = "SetScoreFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param teamName name of the team whose score is evaluated.
         * @return A new instance of fragment SetScoreFragment.
         */
        @JvmStatic
        fun getInstance(teamName: String, oppTeamName: String) =
            SetScoreFragment().apply {
                arguments = Bundle().apply {
                    putString(TEAM_NAME, teamName)
                    putString(OPP_TEAM_NAME, oppTeamName)
                }
            }
    }

    // Team Name Variables
    private var teamName: String? = null
    private var oppTeamName: String? = null


    private lateinit var setScoreListener: SetScoreListener

    // Ui State Variables
    private var tichuClicks: Int = 0
    private var grandTichuClicks: Int = 0
    private var oppTichuClicks: Int = 0
    private var oppGrandTichuClicks: Int = 0

    // Listener Interface for SetScoreFragment
    interface SetScoreListener{
        fun onTichuClicked(setScoreFragment: SetScoreFragment)
        fun onOppTichuClicked(setScoreFragment: SetScoreFragment)
        fun onGrandTichuClicked(setScoreFragment: SetScoreFragment)
        fun onOppGrandTichuClicked(setScoreFragment: SetScoreFragment)
        fun onDoubleWinClicked(setScoreFragment: SetScoreFragment)
        fun onOkClicked(setScoreFragment: SetScoreFragment, value: Int)
        fun onRemoveClicked(setScoreFragment: SetScoreFragment)
    }

    // Create Fragment and set team names
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try{
            setScoreListener = context as SetScoreListener
            Log.d(TAG, "Listener instantiated.")
        } catch(e: ClassCastException){
            throw ClassCastException("$context must implement SetScoreListener.")
        }

        arguments?.let {
            teamName = it.getString(TEAM_NAME)
            oppTeamName = it.getString(OPP_TEAM_NAME)
        }
    }

    // Create View, initialize ui and set listeners to important components
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = LayoutInflater.from(context).inflate(R.layout.fragment_set_score, null, false)

        fragment.findViewById<TextView>(R.id.tv_fragment_score).text = resources.getString(R.string.round_score, teamName)
        fragment.findViewById<TextView>(R.id.tv_roundscore_team1).text = teamName
        fragment.findViewById<TextView>(R.id.tv_roundscore_team2).text = oppTeamName

        val bTichu = fragment.findViewById<MaterialButton>(R.id.b_tichu)
        val bOppTichu = fragment.findViewById<MaterialButton>(R.id.b_tichu_opponent)
        val bGrandTichu = fragment.findViewById<MaterialButton>(R.id.b_grandtichu)
        val bOppGrandTichu = fragment.findViewById<MaterialButton>(R.id.b_grandtichu_opponent)
        val bDoubleWin = fragment.findViewById<MaterialButton>(R.id.b_doublewin)
        val bOk = fragment.findViewById<MaterialButton>(R.id.b_submit)
        val bRemove = fragment.findViewById<ImageButton>(R.id.b_remove)
        val npScore = fragment.findViewById<NumberPicker>(R.id.np_scorepicker)
        val bonzai = ResourcesCompat.getFont(requireContext(), R.font.bonzai)

        val valuesArray: Array<String> = resources.getStringArray(R.array.scores)

        npScore.setSelectedTypeface(bonzai)
        npScore.typeface = bonzai
        npScore.minValue = 0
        npScore.maxValue = 30
        npScore.value = 0
        npScore.displayedValues = valuesArray

        bTichu.setOnClickListener {
            setBackgroundTintOfView(bGrandTichu, R.color.yellow)
            grandTichuClicks = 0
            flipTeamButtonColors(it, tichuClicks++)
            setScoreListener.onTichuClicked(this)
        }

        bOppTichu.setOnClickListener {
            setBackgroundTintOfView(bOppGrandTichu, R.color.yellow)
            oppGrandTichuClicks = 0
            flipOppButtonColors(it, oppTichuClicks++)
            setScoreListener.onOppTichuClicked(this)
        }

        bGrandTichu.setOnClickListener {
            setBackgroundTintOfView(bTichu, R.color.yellow)
            tichuClicks = 0
            flipTeamButtonColors(it, grandTichuClicks++)
            setScoreListener.onGrandTichuClicked(this)
        }

        bOppGrandTichu.setOnClickListener {
            setBackgroundTintOfView(bOppTichu, R.color.yellow)
            oppTichuClicks = 0
            flipOppButtonColors(it, oppGrandTichuClicks++)
            setScoreListener.onOppGrandTichuClicked(this)
        }

        bDoubleWin.setOnClickListener { setScoreListener.onDoubleWinClicked(this) }

        bOk.setOnClickListener {
            val score = valuesArray[npScore.value].toInt()
            tichuClicks = 0
            grandTichuClicks = 0
            setScoreListener.onOkClicked(this, score)
        }

        bRemove.setOnClickListener {
            tichuClicks = 0
            grandTichuClicks = 0
            setScoreListener.onRemoveClicked(this)
        }

        return fragment
    }

    // Flip button colors according to success, failure or neutral states
    private fun flipTeamButtonColors(it: View, clicks: Int){
        val success = 0
        val failure = 1
        val neutral = 2

        when (clicks%3) {
            success -> {
                setBackgroundTintOfView(it, R.color.green)
                it.rootView.findViewById<MaterialButton>(R.id.b_doublewin).isEnabled = true
            }
            failure -> {
                setBackgroundTintOfView(it, R.color.red)
                it.rootView.findViewById<MaterialButton>(R.id.b_doublewin).isEnabled = false
            }
            neutral -> {
                setBackgroundTintOfView(it, R.color.yellow)
                it.rootView.findViewById<MaterialButton>(R.id.b_doublewin).isEnabled = true
            }
        }
    }

    // Flip button colors according to success, failure or neutral states
    private fun flipOppButtonColors(it: View, clicks: Int){
        val success = 0
        val failure = 1
        val neutral = 2

        when (clicks%3) {
            success -> {
                setBackgroundTintOfView(it, R.color.green)
                it.rootView.findViewById<MaterialButton>(R.id.b_doublewin).isEnabled = false
            }
            failure -> {
                setBackgroundTintOfView(it, R.color.red)
                it.rootView.findViewById<MaterialButton>(R.id.b_doublewin).isEnabled = true
            }
            neutral -> {
                setBackgroundTintOfView(it, R.color.yellow)
                it.rootView.findViewById<MaterialButton>(R.id.b_doublewin).isEnabled = true
            }
        }
    }

    // Set Background Color of View
    private fun setBackgroundTintOfView(it: View, color: Int){
        it.backgroundTintList = ContextCompat.getColorStateList(requireContext(), color)
    }
}