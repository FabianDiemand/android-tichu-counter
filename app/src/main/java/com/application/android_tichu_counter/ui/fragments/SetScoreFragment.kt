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
class SetScoreFragment : Fragment() {
    companion object {
        private const val TEAM_NAME = "teamname"
        private const val OPP_TEAM_NAME = "oppteamname"

        private const val TAG = "SetScoreFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param teamName name of the team whose score is evaluated.
         * @param oppTeamName name of the team whose score is evaluated.
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

    private lateinit var bTichu: MaterialButton
    private lateinit var bOppTichu: MaterialButton
    private lateinit var bGrandTichu: MaterialButton
    private lateinit var bOppGrandTichu: MaterialButton
    private lateinit var tvError: TextView
    private lateinit var bDoubleWin: MaterialButton
    private lateinit var bOk: MaterialButton
    private lateinit var ibRemove: ImageButton
    private lateinit var npScore: NumberPicker

    // Team Name Variables
    private var teamName: String? = null
    private var oppTeamName: String? = null

    private lateinit var setScoreListener: SetScoreListener

    // Ui State Variables
    private var tichuResult = TichuState.NEUTRAL
    private var grandTichuResult = TichuState.NEUTRAL
    private var oppTichuResult = TichuState.NEUTRAL
    private var oppGrandTichuResult = TichuState.NEUTRAL

    private lateinit var npValuesArray: Array<String>

    // Listener Interface for SetScoreFragment
    interface SetScoreListener {
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

        try {
            setScoreListener = context as SetScoreListener
            Log.d(TAG, "Listener instantiated.")
        } catch (e: ClassCastException) {
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
        val fragment =
            LayoutInflater.from(context).inflate(R.layout.fragment_set_score, null, false)

        fragment.findViewById<TextView>(R.id.tv_fragment_score).text =
            resources.getString(R.string.round_score, teamName)
        fragment.findViewById<TextView>(R.id.tv_roundscore_team1).text = teamName
        fragment.findViewById<TextView>(R.id.tv_roundscore_team2).text = oppTeamName

        bTichu = fragment.findViewById(R.id.b_tichu)
        bOppTichu = fragment.findViewById(R.id.b_tichu_opponent)
        bGrandTichu = fragment.findViewById(R.id.b_grandtichu)
        bOppGrandTichu = fragment.findViewById(R.id.b_grandtichu_opponent)
        tvError = fragment.findViewById(R.id.tv_error)
        bDoubleWin = fragment.findViewById(R.id.b_doublewin)
        bOk = fragment.findViewById(R.id.b_submit)
        ibRemove = fragment.findViewById(R.id.b_remove)
        npScore = fragment.findViewById(R.id.np_scorepicker)

        initializeNumberPicker()
        setOnClickListeners()

        return fragment
    }

    private fun initializeNumberPicker() {
        val bonzai = ResourcesCompat.getFont(requireContext(), R.font.bonzai)

        npValuesArray = resources.getStringArray(R.array.scores)

        npScore.setSelectedTypeface(bonzai)
        npScore.typeface = bonzai
        npScore.minValue = 0
        npScore.maxValue = 30
        npScore.value = 0
        npScore.displayedValues = npValuesArray

    }

    private fun setOnClickListeners() {
        bTichu.setOnClickListener {
            setBackgroundTintOfView(bGrandTichu, R.color.yellow)
            grandTichuResult = TichuState.NEUTRAL

            tichuResult = tichuResult.nextState()
            handleUiChange(it, tichuResult)

            setScoreListener.onTichuClicked(this)

            Log.d(TAG, createState())
        }

        bOppTichu.setOnClickListener {
            setBackgroundTintOfView(bOppGrandTichu, R.color.yellow)
            oppGrandTichuResult = TichuState.NEUTRAL

            oppTichuResult = oppTichuResult.nextState()
            handleUiChange(it, oppTichuResult)

            setScoreListener.onOppTichuClicked(this)

            Log.d(TAG, createState())
        }

        bGrandTichu.setOnClickListener {
            setBackgroundTintOfView(bTichu, R.color.yellow)
            tichuResult = TichuState.NEUTRAL

            grandTichuResult = grandTichuResult.nextState()
            handleUiChange(it, grandTichuResult)

            setScoreListener.onGrandTichuClicked(this)

            Log.d(TAG, createState())
        }

        bOppGrandTichu.setOnClickListener {
            setBackgroundTintOfView(bOppTichu, R.color.yellow)
            oppTichuResult = TichuState.NEUTRAL

            oppGrandTichuResult = oppGrandTichuResult.nextState()
            handleUiChange(it, oppGrandTichuResult)

            setScoreListener.onOppGrandTichuClicked(this)

            Log.d(TAG, createState())
        }

        bDoubleWin.setOnClickListener { setScoreListener.onDoubleWinClicked(this) }

        bOk.setOnClickListener {
            val score = npValuesArray[npScore.value].toInt()
            setScoreListener.onOkClicked(this, score)
        }

        ibRemove.setOnClickListener {
            setScoreListener.onRemoveClicked(this)
        }
    }

    private fun handleUiChange(it: View, result: TichuState) {
        flipTeamButtonColors(it, result)

        bDoubleWin.isEnabled = teamWinPossible()

        if (tichuCombinationPossible()) {
            bDoubleWin.isEnabled = true
            bOk.isEnabled = true
            tvError.text = ""
        } else {
            bDoubleWin.isEnabled = false
            bOk.isEnabled = false
            tvError.text = getString(R.string.error_tichu_conflict)
        }
    }

    // Flip button colors according to success, failure or neutral states
    private fun flipTeamButtonColors(it: View, state: TichuState) {
        when (state) {
            TichuState.SUCCESS -> {
                setBackgroundTintOfView(it, R.color.green)
            }
            TichuState.FAILURE -> {
                setBackgroundTintOfView(it, R.color.red)
            }
            TichuState.NEUTRAL -> {
                setBackgroundTintOfView(it, R.color.yellow)
            }
        }
    }

    // Set Background Color of View
    private fun setBackgroundTintOfView(it: View, color: Int) {
        it.backgroundTintList = ContextCompat.getColorStateList(requireContext(), color)
    }

    private fun teamWinPossible(): Boolean {
        return oppTichuResult != TichuState.SUCCESS
                && oppGrandTichuResult != TichuState.SUCCESS
                && tichuResult != TichuState.FAILURE
                && grandTichuResult != TichuState.FAILURE
    }

    private fun tichuCombinationPossible(): Boolean {
        var possible = true

        if (tichuResult == TichuState.SUCCESS && oppTichuResult == TichuState.SUCCESS) {
            possible = false
        } else if (tichuResult == TichuState.SUCCESS && oppGrandTichuResult == TichuState.SUCCESS) {
            possible = false
        } else if (grandTichuResult == TichuState.SUCCESS && oppTichuResult == TichuState.SUCCESS) {
            possible = false
        } else if (grandTichuResult == TichuState.SUCCESS && oppGrandTichuResult == TichuState.SUCCESS) {
            possible = false
        }

        return possible
    }

    private fun createState(): String {
        return "Tichu State:" +
                "\nOwn Tichu:        $tichuResult" +
                "\nOwn Grand Tichu:  $grandTichuResult" +
                "\nOpp Tichu:        $oppTichuResult" +
                "\nOpp Grand Tichu:  $oppGrandTichuResult\n"
    }

    enum class TichuState(val state: Int) {
        NEUTRAL(0) {
            override fun nextState() = SUCCESS
        },
        SUCCESS(1) {
            override fun nextState() = FAILURE
        },
        FAILURE(2) {
            override fun nextState() = NEUTRAL
        };

        abstract fun nextState(): TichuState
    }
}