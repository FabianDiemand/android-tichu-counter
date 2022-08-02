package com.application.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.databinding.FragmentSetScoreBinding

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

    private var _binding: FragmentSetScoreBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentSetScoreBinding.inflate(inflater, null, false)
        val view = binding.root

        initializeNumberPicker()
        setupUi()
        setOnClickListeners()

        return view
    }

    private fun setupUi() {
        binding.tvFragmentScore.text = resources.getString(R.string.round_score, teamName)
        binding.tvRoundscoreTeam1.text = teamName
        binding.tvRoundscoreTeam2.text = oppTeamName
    }

    private fun initializeNumberPicker() {
        val bonzai = ResourcesCompat.getFont(requireContext(), R.font.bonzai)

        npValuesArray = resources.getStringArray(R.array.scores)

        with(binding){
            npScorepicker.setSelectedTypeface(bonzai)
            npScorepicker.typeface = bonzai
            npScorepicker.minValue = 0
            npScorepicker.maxValue = 30
            npScorepicker.value = 0
            npScorepicker.displayedValues = npValuesArray
        }

    }

    private fun setOnClickListeners() {
        with(binding){
            bTichu.setOnClickListener {
                setBackgroundTintOfView(bGrandtichu, R.color.yellow)
                grandTichuResult = TichuState.NEUTRAL

                tichuResult = tichuResult.nextState()
                handleUiChange(it, tichuResult)

                setScoreListener.onTichuClicked(this@SetScoreFragment)

                Log.d(TAG, createState())
            }

            bTichuOpponent.setOnClickListener {
                setBackgroundTintOfView(bGrandtichuOpponent, R.color.yellow)
                oppGrandTichuResult = TichuState.NEUTRAL

                oppTichuResult = oppTichuResult.nextState()
                handleUiChange(it, oppTichuResult)

                setScoreListener.onOppTichuClicked(this@SetScoreFragment)

                Log.d(TAG, createState())
            }

            bGrandtichu.setOnClickListener {
                setBackgroundTintOfView(bTichu, R.color.yellow)
                tichuResult = TichuState.NEUTRAL

                grandTichuResult = grandTichuResult.nextState()
                handleUiChange(it, grandTichuResult)

                setScoreListener.onGrandTichuClicked(this@SetScoreFragment)

                Log.d(TAG, createState())
            }

            bGrandtichuOpponent.setOnClickListener {
                setBackgroundTintOfView(bTichuOpponent, R.color.yellow)
                oppTichuResult = TichuState.NEUTRAL

                oppGrandTichuResult = oppGrandTichuResult.nextState()
                handleUiChange(it, oppGrandTichuResult)

                setScoreListener.onOppGrandTichuClicked(this@SetScoreFragment)

                Log.d(TAG, createState())
            }

            bDoublewin.setOnClickListener { setScoreListener.onDoubleWinClicked(this@SetScoreFragment) }

            bSubmit.setOnClickListener {
                val score = npValuesArray[npScorepicker.value].toInt()
                setScoreListener.onOkClicked(this@SetScoreFragment, score)
            }

            bRemove.setOnClickListener {
                setScoreListener.onRemoveClicked(this@SetScoreFragment)
            }
        }

    }

    private fun handleUiChange(it: View, result: TichuState) {
        flipTeamButtonColors(it, result)

        with(binding){
            bDoublewin.isEnabled = teamWinPossible()

            if (tichuCombinationPossible()) {
                bDoublewin.isEnabled = true
                bSubmit.isEnabled = true
                tvError.text = ""
            } else {
                bDoublewin.isEnabled = false
                bSubmit.isEnabled = false
                tvError.text = getString(R.string.error_tichu_conflict)
            }
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