package com.application.android_tichu_counter.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.viewmodel.GameViewModel
import com.application.android_tichu_counter.data.viewmodel.RoundViewModel
import com.application.android_tichu_counter.databinding.ActivityScoreboardBinding
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_1
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_2
import com.application.android_tichu_counter.ui.fragments.CongratulationFragment
import com.application.android_tichu_counter.ui.fragments.SetScoreFragment
import kotlinx.coroutines.launch

/**
 * ScoreboardActivity to display points and game progress
 *
 * Extends BaseActivity to be affine to in-app language changes.
 * Implements SetScoreListener to listen to inputs in the SetScoreFragment.
 *
 * @property KEY_TEAM_1 intent key for team name 1
 * @property KEY_TEAM_2 intent key for team name 2
 *
 * @author Devtronaut
 */
class ScoreboardActivity : BaseActivity(), SetScoreFragment.SetScoreListener,
    CongratulationFragment.CongratulationListener {
    companion object {
        private const val TAG = "ScoreboardActivity"

        const val KEY_GAME_ID = "Game_Id"
        const val KEY_TEAM_1 = "Team_1"
        const val KEY_TEAM_2 = "Team_2"
    }

    private lateinit var binding: ActivityScoreboardBinding

    private lateinit var gameViewModel: GameViewModel
    private lateinit var roundViewModel: RoundViewModel

    // Activity variables
    private var setScoreFragment: SetScoreFragment? = null
    private var congratulationFragment: CongratulationFragment? = null

    private var scoringTeamId: Int = -1

    private var currentGameId: String = ""
    private lateinit var currentGame: Game
    private lateinit var currentRounds: List<Round>
    private var round: Round? = null
    private var roundsPlayed: Int = 0

    /** Create view, instantiate ui components, set listeners. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        gameViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameViewModel::class.java]

        roundViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RoundViewModel::class.java]

        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    // Set listeners to important ui components
    private fun setListeners() {
        with(binding) {
            ibBackbutton.setOnClickListener {
                onBackPressed()
            }

            llTeam1.setOnClickListener {
                showSetScoreDialog(tvTeamname1.text.toString(), tvTeamname2.text.toString())
                scoringTeamId = it.id
            }

            llTeam2.setOnClickListener {
                showSetScoreDialog(tvTeamname2.text.toString(), tvTeamname1.text.toString())
                scoringTeamId = it.id
            }

            vGrayBackground.setOnClickListener {
                onRemoveClicked(setScoreFragment!!)
            }

            bUndo.setOnClickListener {
                deleteLastRound()
            }
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    // Initialize the scores and the team names
    private fun initializeUi() {
        processIntent()

        binding.trHeaderTeam1.visibility = View.GONE
        binding.trHeaderTeam2.visibility = View.GONE

        binding.tvScore1.text = "0"
        binding.tvScore2.text = "0"
    }

    // Read the teamnames from the intent or set defaults if no teamnames are put in the extras
    private fun processIntent() {
        if (intent.hasExtra(KEY_GAME_ID)) {
            currentGameId = intent.getStringExtra(KEY_GAME_ID).toString()
        } else {
            val firstName = if (intent.hasExtra(KEY_TEAM_1)) {
                intent.getStringExtra(KEY_TEAM_1)
            } else {
                getString(R.string.default_teamname_1)
            }

            val secondName = if (intent.hasExtra(KEY_TEAM_2)) {
                intent.getStringExtra(KEY_TEAM_2)
            } else {
                getString(R.string.default_teamname_2)
            }

            currentGame = Game(firstName!!, secondName!!)
            currentGameId = currentGame.gameId
            gameViewModel.addGame(currentGame)
        }

        observeGameWithRounds(currentGameId)
    }

    @SuppressLint("InflateParams")
    private fun observeGameWithRounds(gameId: String) {
        lifecycleScope.launch {
            gameViewModel.getGameWithRounds(gameId).collect { gameWithRounds ->
                currentGame = gameWithRounds.game
                currentRounds = gameWithRounds.rounds
                val rounds = gameWithRounds.rounds
                roundsPlayed = rounds.size

                binding.tvTeamname1.text = currentGame.firstTeam
                binding.tvScore1.text = currentGame.firstTeamScore.toString()

                binding.tvTeamname2.text = currentGame.secondTeam
                binding.tvScore2.text = currentGame.secondTeamScore.toString()

                binding.tlRoundsTeam1.removeAllViews()
                binding.tlRoundsTeam2.removeAllViews()

                if (rounds.isNotEmpty()) {
                    binding.bUndo.visibility = View.VISIBLE
                    binding.trHeaderTeam1.visibility = View.VISIBLE
                    binding.trHeaderTeam2.visibility = View.VISIBLE
                } else {
                    binding.bUndo.visibility = View.GONE
                    binding.trHeaderTeam1.visibility = View.GONE
                    binding.trHeaderTeam2.visibility = View.GONE
                }

                if (currentGame.finished) {
                    disableClickableViews()
                    binding.bUndo.visibility = View.GONE
                } else {
                    enableClickableViews()
                }

                rounds.asReversed().forEach { round ->
                    val trTeam1 = layoutInflater.inflate(R.layout.round_result_tr, null, false)
                    val trTeam2 = layoutInflater.inflate(R.layout.round_result_tr, null, false)

                    trTeam1.findViewById<TextView>(R.id.tv_round_points).text =
                        round.firstTeamRoundScore.toString()
                    trTeam2.findViewById<TextView>(R.id.tv_round_points).text =
                        round.secondTeamRoundScore.toString()

                    with(round) {
                        setTichuX(trTeam1.findViewById(R.id.tv_round_tichu), firstTeamTichu)

                        setTichuX(trTeam2.findViewById(R.id.tv_round_tichu), secondTeamTichu)

                        setTichuX(
                            trTeam1.findViewById(R.id.tv_round_grand_tichu),
                            firstTeamGrandtichu
                        )

                        setTichuX(
                            trTeam2.findViewById(R.id.tv_round_grand_tichu),
                            secondTeamGrandtichu
                        )

                        if (firstTeamDoubleWin) {
                            setGreenX(trTeam1.findViewById(R.id.tv_round_double_win))
                        }

                        if (secondTeamDoubleWin) {
                            setGreenX(trTeam2.findViewById(R.id.tv_round_double_win))
                        }
                    }

                    binding.tlRoundsTeam1.addView(trTeam1)
                    binding.tlRoundsTeam2.addView(trTeam2)
                }
            }
        }
    }

    private fun setTichuX(textView: TextView, success: Boolean?) {
        if (success == true) {
            setGreenX(textView)
        } else if (success == false) {
            setRedX(textView)
        }
    }

    private fun setGreenX(textView: TextView) {
        textView.text = getString(R.string.X)
        textView.setTextColor(
            resolveColor(R.color.green)
        )
    }

    private fun setRedX(textView: TextView) {
        textView.text = getString(R.string.X)
        textView.setTextColor(
            resolveColor(R.color.red)
        )
    }

    private fun resolveColor(id: Int): Int {
        return resources.getColor(id, application.theme)
    }

    /** Remove the gray view from the screen and finish the activity */
    override fun onBackPressed() {
        binding.vGrayBackground.visibility = View.GONE

        super.onBackPressed()
    }

    /** Change the round state variables for tichu according to the user input */
    override fun onTichuClicked(setScoreFragment: SetScoreFragment) {
        round?.changeFirstTeamTichu()
    }

    /** Change the round state variables for opponent tichu according to the user input */
    override fun onOppTichuClicked(setScoreFragment: SetScoreFragment) {
        round?.changeSecondTeamTichu()
    }

    /** Change the round state variables for grand tichu according to the user input */
    override fun onGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        round?.changeFirstTeamGrandtichu()
    }

    /** Change the round state variables for opponent grand tichu according to the user input */
    override fun onOppGrandTichuClicked(setScoreFragment: SetScoreFragment) {
        round?.changeSecondTeamGrandtichu()
    }

    /** Calculate the new score with a double win */
    override fun onDoubleWinClicked(setScoreFragment: SetScoreFragment) {
        when (scoringTeamId) {
            binding.llTeam1.id -> round?.setFirstTeamDoubleWin()
            binding.llTeam2.id -> round?.setSecondTeamDoubleWin()
        }

        val firstTeamRoundScore = round?.calculateFirstTeamScore(0)!!
        val secondTeamRoundScore = round?.calculateSecondTeamScore(0)!!

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)
        finishRound(firstTeamRoundScore, secondTeamRoundScore)
    }

    /** Calculate the new score on a normal round (no double win) */
    override fun onOkClicked(setScoreFragment: SetScoreFragment, value: Int) {
        var firstTeamRoundScore = 0
        var secondTeamRoundScore = 0

        when (scoringTeamId) {
            binding.llTeam1.id -> {
                firstTeamRoundScore = round?.calculateFirstTeamScore(value)!!
                secondTeamRoundScore = round?.calculateSecondTeamScore(100 - value)!!
            }
            binding.llTeam2.id -> {
                secondTeamRoundScore = round?.calculateSecondTeamScore(value)!!
                firstTeamRoundScore = round?.calculateFirstTeamScore(100 - value)!!
            }
        }

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)
        finishRound(firstTeamRoundScore, secondTeamRoundScore)
    }

    private fun finishRound(firstTeamRoundScore: Int, secondTeamRoundScore: Int) {
        roundViewModel.addRound(round!!)

        currentGame.firstTeamScore += firstTeamRoundScore
        currentGame.secondTeamScore += secondTeamRoundScore

        if (evaluateWin()) {
            currentGame.finished = true
        }

        gameViewModel.updateGame(currentGame)

        round = null
    }

    private fun evaluateWin(): Boolean {
        with(currentGame) {
            if (firstTeamScore >= 1000 && secondTeamScore >= 1000) {
                if (firstTeamScore > secondTeamScore) {
                    showCongratulationFragment(
                        binding.tvTeamname1.text.toString(),
                        firstTeamScore,
                        secondTeamScore
                    )
                    return true
                } else if (secondTeamScore > firstTeamScore) {
                    showCongratulationFragment(
                        binding.tvTeamname2.text.toString(),
                        secondTeamScore,
                        firstTeamScore
                    )
                    return true
                }
            } else if (firstTeamScore >= 1000) {
                showCongratulationFragment(
                    binding.tvTeamname1.text.toString(),
                    firstTeamScore,
                    secondTeamScore
                )
                return true
            } else if (secondTeamScore >= 1000) {
                showCongratulationFragment(
                    binding.tvTeamname2.text.toString(),
                    secondTeamScore,
                    firstTeamScore
                )
                return true
            }
        }

        return false
    }

    /** Reset all the state variables, enable UIs */
    override fun onRemoveClicked(setScoreFragment: SetScoreFragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .remove(setScoreFragment)
            .commit()

        supportFragmentManager.popBackStack()

        binding.vGrayBackground.visibility = View.GONE
        flipComponentsEnabled()
    }

    private fun showCongratulationFragment(
        winningTeam: String,
        winningScore: Int,
        losingScore: Int
    ) {
        congratulationFragment =
            CongratulationFragment.getInstance(winningTeam, winningScore, losingScore)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_congrats, congratulationFragment!!)
            .addToBackStack(congratulationFragment!!.tag)
            .commit()

        // Make gray background visible to simulate dialog behaviour
        binding.vGrayBackground.visibility = View.VISIBLE
        // Disable views in the background
        flipComponentsEnabled()

        Log.d(TAG, "Showing congratulation fragment!")
    }

    private fun removeCongratulationFragment(congratulationFragment: CongratulationFragment) {
        supportFragmentManager
            .beginTransaction()
            .remove(congratulationFragment)
            .commit()

        supportFragmentManager.popBackStack()

        binding.vGrayBackground.visibility = View.GONE
        flipComponentsEnabled()
    }

    override fun onThanksButtonClicked(congratulationFragment: CongratulationFragment) {
        removeCongratulationFragment(congratulationFragment)
    }

    // Show the SetScoreDialog with focus on the team that enters its score
    private fun showSetScoreDialog(teamName: String, oppTeamName: String) {
        setScoreFragment = SetScoreFragment.getInstance(teamName, oppTeamName)

        supportFragmentManager.beginTransaction()
            // Set slide-in/ slide-out animations
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .replace(R.id.fcv_set_score, setScoreFragment!!)
            .addToBackStack(setScoreFragment!!.tag)
            .commit()

        // Make gray background visible to simulate dialog behaviour
        binding.vGrayBackground.visibility = View.VISIBLE
        // Disable views in the background
        flipComponentsEnabled()

        round = Round(currentGame.gameId, ++roundsPlayed)
    }

    // Flip enabled state of layout key components
    private fun flipComponentsEnabled() {
        with(binding) {
            ibBackbutton.isEnabled = !ibBackbutton.isEnabled
            llTeam1.isEnabled = !llTeam1.isEnabled
            llTeam2.isEnabled = !llTeam2.isEnabled
        }
    }

    private fun disableClickableViews() {
        with(binding) {
            llTeam1.isEnabled = false
            llTeam2.isEnabled = false
            llTeam1.isFocusable = false
            llTeam2.isFocusable = false
        }
    }

    private fun enableClickableViews() {
        with(binding) {
            llTeam1.isEnabled = true
            llTeam2.isEnabled = true
            llTeam1.isFocusable = false
            llTeam2.isFocusable = false
        }
    }

    private fun deleteLastRound() {
        val lastRound = currentRounds[currentRounds.size - 1]

        currentGame.firstTeamScore -= lastRound.firstTeamRoundScore
        currentGame.secondTeamScore -= lastRound.secondTeamRoundScore
        gameViewModel.updateGame(currentGame)

        roundViewModel.deleteRound(lastRound)
    }
}