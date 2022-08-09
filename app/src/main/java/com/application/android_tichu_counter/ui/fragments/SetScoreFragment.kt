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
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.databinding.FragmentSetScoreBinding
import com.application.android_tichu_counter.domain.enums.teams.Team
import com.application.android_tichu_counter.domain.enums.teams.Team.*
import com.application.android_tichu_counter.domain.enums.tichu_states.TichuState

/**
 * Fragment to set tichus and the score
 */
class SetScoreFragment : Fragment() {
    companion object {
        private const val SCORING_TEAM = "scoringteam"
        private const val TEAM_NAME = "teamname"
        private const val OPP_TEAM_NAME = "oppteamname"
        private const val CURRENT_ROUND = "currentround"

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
        fun getInstance(
            scoringTeam: Team,
            teamName: String,
            oppTeamName: String,
            currentRound: Round
        ) =
            SetScoreFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(SCORING_TEAM, scoringTeam)
                    putString(TEAM_NAME, teamName)
                    putString(OPP_TEAM_NAME, oppTeamName)
                    putParcelable(CURRENT_ROUND, currentRound)
                }
            }
    }

    private var _binding: FragmentSetScoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var round: Round

    // Team Name Variables
    private lateinit var scoringTeam: Team
    private lateinit var teamName: String
    private lateinit var oppTeamName: String

    private lateinit var setScoreListener: SetScoreListener

    private lateinit var npValuesArray: Array<String>

    // Listener Interface for SetScoreFragment
    interface SetScoreListener {
        fun onReturnWithResult(setScoreFragment: SetScoreFragment, currentRound: Round)
        fun onAbort(setScoreFragment: SetScoreFragment)
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

        requireArguments().let {
            scoringTeam = it.getSerializable(SCORING_TEAM) as Team
            teamName = it.getString(TEAM_NAME)!!
            oppTeamName = it.getString(OPP_TEAM_NAME)!!

            round = it.getParcelable<Round>(CURRENT_ROUND) as Round
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

        setupUi()
        setOnClickListeners()

        return view
    }

    private fun setupUi() {
        val team = if (scoringTeam == FIRST_TEAM) teamName else oppTeamName
        binding.tvFragmentScore.text = resources.getString(R.string.round_score, team)

        binding.tvRoundscoreTeam1.text = teamName
        binding.tvRoundscoreTeam2.text = oppTeamName

        initializeNumberPicker()
    }

    private fun initializeNumberPicker() {
        val bonzaiFont = ResourcesCompat.getFont(requireContext(), R.font.bonzai)

        npValuesArray = resources.getStringArray(R.array.scores)

        with(binding) {
            npScorepicker.setSelectedTypeface(bonzaiFont)
            npScorepicker.typeface = bonzaiFont
            npScorepicker.minValue = 0
            npScorepicker.maxValue = 30
            npScorepicker.value = 0
            npScorepicker.displayedValues = npValuesArray
        }

    }

    private fun setOnClickListeners() {
        with(binding) {
            bTichu.setOnClickListener {
                round.changeTichuForTeam(FIRST_TEAM)
                renderUi()
            }

            bTichuOpponent.setOnClickListener {
                round.changeTichuForTeam(SECOND_TEAM)
                renderUi()
            }

            bGrandtichu.setOnClickListener {
                round.changeGrandTichuForTeam(FIRST_TEAM)
                renderUi()
            }

            bGrandtichuOpponent.setOnClickListener {
                round.changeGrandTichuForTeam(SECOND_TEAM)
                renderUi()
            }

            bDoublewin.setOnClickListener {
                round.setDoubleWinForTeam(scoringTeam)
                finishRound()
            }

            bSubmit.setOnClickListener {
                finishRound()
            }

            bRemove.setOnClickListener {
                setScoreListener.onAbort(this@SetScoreFragment)
            }
        }
    }

    private fun finishRound() {
        val roundScore = npValuesArray[binding.npScorepicker.value].toInt()
        round.calculateScoreByTeam(scoringTeam, roundScore)

        setScoreListener.onReturnWithResult(this, round)
    }

    private fun renderUi() {
        with(binding) {
            setColorByState(bTichu, round.firstTeamTichu)
            setColorByState(bGrandtichu, round.firstTeamGrandtichu)
            setColorByState(bTichuOpponent, round.secondTeamTichu)
            setColorByState(bGrandtichuOpponent, round.secondTeamGrandtichu)

            val doubleWinPossible = round.isDoubleWinPossibleForTeam(scoringTeam)
            val validTichuState = round.isValidState()

            bDoublewin.isEnabled = validTichuState && doubleWinPossible
            bSubmit.isEnabled = validTichuState
            tvError.text = if (validTichuState) "" else getString(R.string.error_tichu_conflict)
        }
    }

    private fun setColorByState(it: View, state: TichuState) {
        it.backgroundTintList = ContextCompat.getColorStateList(requireContext(), state.getColor())
    }
}