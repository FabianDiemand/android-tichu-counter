package com.application.android_tichu_counter.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.application.android_tichu_counter.R

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

    private var winnerName: String? = null
    private var winnerScore: Int? = null
    private var loserScore: Int? = null

    private lateinit var congratulationListener: CongratulationListener

    private lateinit var bThanks: TextView

    interface CongratulationListener{
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
    ): View? {
        // Inflate the layout for this fragment
        val fragment = LayoutInflater.from(context).inflate(R.layout.fragment_congratulation, null, false)

        fragment.findViewById<TextView>(R.id.tv_winning_team).text = winnerName
        fragment.findViewById<TextView>(R.id.tv_winning_score).text = getString(R.string.scores, winnerScore, loserScore)

        bThanks = fragment.findViewById(R.id.b_thanks)

        setOnClickListeners()

        return fragment
    }

    private fun setOnClickListeners(){
        bThanks.setOnClickListener {
            congratulationListener.onThanksButtonClicked(this)
        }
    }

}