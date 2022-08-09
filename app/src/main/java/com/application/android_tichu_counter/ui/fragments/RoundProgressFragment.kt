package com.application.android_tichu_counter.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.data.entities.TeamRound
import com.application.android_tichu_counter.databinding.FragmentRoundProgressBinding
import com.application.android_tichu_counter.databinding.RoundResultTrBinding
import com.application.android_tichu_counter.domain.enums.tichu_states.TichuState

private const val TEAM_ROUND = "team_round"

class RoundProgressFragment : Fragment() {
    companion object {
        @JvmStatic
        fun getInstance(roundOfTeam: ArrayList<TeamRound>) =
            RoundProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(TEAM_ROUND, roundOfTeam)
                }
            }
    }

    private var _binding: FragmentRoundProgressBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var teamRounds: ArrayList<TeamRound>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamRounds = it.getParcelableArrayList<TeamRound>(TEAM_ROUND) as ArrayList<TeamRound>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRoundProgressBinding.inflate(inflater, null, false)
        val view = binding.root

        fillRounds()

        return view
    }

    private fun fillRounds() {
        teamRounds.forEach { round ->
            val trRound = RoundResultTrBinding.inflate(layoutInflater)

            trRound.tvRoundPoints.text = round.roundScore.toString()

            with(round) {
                setTichu(trRound.tvRoundTichu, tichu)
                setGrandTichu(trRound.tvRoundGrandTichu, grandTichu)

                if (doubleWin) setDoubleWin(trRound.tvRoundDoubleWin)
            }

            binding.tlRoundsTeam.addView(trRound.root)
        }
    }

    private fun setTichu(textView: TextView, tichuState: TichuState) {
        if (tichuState == TichuState.SUCCESS) {
            textView.setText(R.string.tichu_short)
            textView.setTextColor(resolveColor(R.color.green))
        } else if (tichuState == TichuState.FAILURE) {
            textView.setText(R.string.tichu_short)
            textView.setTextColor(resolveColor(R.color.red))
        }
    }

    private fun setGrandTichu(textView: TextView, tichuState: TichuState) {
        if (tichuState == TichuState.SUCCESS) {
            textView.setText(R.string.grand_tichu_short)
            textView.setTextColor(resolveColor(R.color.green))
        } else if (tichuState == TichuState.FAILURE) {
            textView.setText(R.string.grand_tichu_short)
            textView.setTextColor(resolveColor(R.color.red))
        }
    }

    private fun setDoubleWin(textView: TextView) {
        textView.setText(R.string.double_win_short)
        textView.setTextColor(resolveColor(R.color.green))
    }

    private fun resolveColor(id: Int): Int {
        return resources.getColor(id, requireActivity().application.theme)
    }
}