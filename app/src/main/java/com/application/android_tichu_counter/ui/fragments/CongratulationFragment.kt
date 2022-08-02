package com.application.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.databinding.FragmentCongratulationBinding

/**
 * A simple [Fragment] subclass.
 * Use the [CongratulationFragment.getInstance] factory method to
 * create an instance of this fragment.
 */
class CongratulationFragment : Fragment() {
    companion object {
        private const val WINNER_NAME = "Winner_Name"
        private const val WINNER_SCORE = "Winner_Score"
        private const val LOSER_SCORE = "Loser_Score"

        private const val TAG = "CongratulationFragment"

        @JvmStatic
        fun getInstance(winnerName: String, winnerScore: Int, loserScore: Int) =
            CongratulationFragment().apply {
                arguments = Bundle().apply {
                    putString(WINNER_NAME, winnerName)
                    putInt(WINNER_SCORE, winnerScore)
                    putInt(LOSER_SCORE, loserScore)
                }
            }
    }

    private var _binding: FragmentCongratulationBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var winnerName: String? = null
    private var winnerScore: Int? = null
    private var loserScore: Int? = null

    private lateinit var congratulationListener: CongratulationListener

    interface CongratulationListener {
        fun onThanksButtonClicked(congratulationFragment: CongratulationFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            congratulationListener = context as CongratulationListener
            Log.d(TAG, "Listener instantiated.")
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement CongratulationListener.")
        }

        arguments?.let {
            winnerName = it.getString(WINNER_NAME)
            winnerScore = it.getInt(WINNER_SCORE)
            loserScore = it.getInt(LOSER_SCORE)
        }
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
            congratulationListener.onThanksButtonClicked(this)
        }
    }
}