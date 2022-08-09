package com.application.android_tichu_counter.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.TichuApplication
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.viewmodel.GameViewModel
import com.application.android_tichu_counter.data.viewmodel.RoundViewModel
import com.application.android_tichu_counter.databinding.ActivityScoreboardBinding
import com.application.android_tichu_counter.databinding.RoundResultTrBinding
import com.application.android_tichu_counter.domain.enums.teams.Team
import com.application.android_tichu_counter.domain.enums.teams.Team.*
import com.application.android_tichu_counter.domain.enums.tichu_states.TichuState
import com.application.android_tichu_counter.domain.enums.tichu_states.TichuState.*
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_1
import com.application.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_2
import com.application.android_tichu_counter.ui.fragments.CongratulationFragment
import com.application.android_tichu_counter.ui.fragments.SetScoreFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    @Inject
    lateinit var gameViewModel: GameViewModel

    @Inject
    lateinit var roundViewModel: RoundViewModel

    // Activity variables
    private var setScoreFragment: SetScoreFragment? = null
    private var congratulationFragment: CongratulationFragment? = null

    private var currentGameId: String = ""
    private lateinit var currentGame: Game
    private lateinit var currentRounds: List<Round>
    private var round: Round? = null
    private var roundsPlayed: Int = 0

    /** Create view, instantiate ui components, set listeners. */
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as TichuApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityScoreboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    // Initialize the scores and the team names
    private fun initializeUi() {
        processIntent()

        binding.trHeaderTeam1.visibility = View.GONE
        binding.trHeaderTeam2.visibility = View.GONE
    }

    // Set listeners to important ui components
    private fun setListeners() {
        with(binding) {
            ibBackbutton.setOnClickListener {
                onBackPressed()
            }

            llTeam1.setOnClickListener {
                showSetScoreDialogForTeam(FIRST_TEAM)
            }

            llTeam2.setOnClickListener {
                showSetScoreDialogForTeam(SECOND_TEAM)
            }

            vGrayBackground.setOnClickListener {
                onAbort(setScoreFragment!!)
            }

            bUndo.setOnClickListener {
                deleteLastRound()
            }
        }

        Log.d(TAG, "Set OnClickListeners")
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
                    renderOverviewState()
                } else {
                    renderForegroundState()
                }

                rounds.asReversed().forEach { round ->
                    val trTeam1 = RoundResultTrBinding.inflate(layoutInflater)
                    val trTeam2 = RoundResultTrBinding.inflate(layoutInflater)

                    trTeam1.tvRoundPoints.text = round.firstTeamRoundScore.toString()
                    trTeam2.tvRoundPoints.text = round.secondTeamRoundScore.toString()

                    with(round) {
                        setTichuX(trTeam1.tvRoundTichu, firstTeamTichu)
                        setTichuX(trTeam2.tvRoundTichu, secondTeamTichu)

                        setTichuX(trTeam1.tvRoundGrandTichu, firstTeamGrandtichu)
                        setTichuX(trTeam2.tvRoundGrandTichu, secondTeamGrandtichu)

                        if (firstTeamDoubleWin) {
                            setGreenX(trTeam1.tvRoundDoubleWin)
                        } else if (secondTeamDoubleWin) {
                            setGreenX(trTeam2.tvRoundDoubleWin)
                        }
                    }

                    binding.tlRoundsTeam1.addView(trTeam1.root)
                    binding.tlRoundsTeam2.addView(trTeam2.root)
                }
            }
        }
    }

    private fun setTichuX(textView: TextView, tichuState: TichuState) {
        if (tichuState == SUCCESS) {
            setGreenX(textView)
        } else if (tichuState == FAILURE) {
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

    /** Calculate the new score on a normal round (no double win) */
    override fun onReturnWithResult(setScoreFragment: SetScoreFragment, currentRound: Round) {
        onAbort(setScoreFragment)
        finishRound(currentRound)
    }

    private fun finishRound(currentRound: Round) {
        currentGame.addFirstTeamScore(currentRound.firstTeamRoundScore)
        currentGame.addSecondTeamScore(currentRound.secondTeamRoundScore)

        if (currentGame.finished) {
            showCongratulationFragment(
                currentGame.getWinningTeamName(),
                currentGame.firstTeamScore,
                currentGame.secondTeamScore
            )
        }

        roundViewModel.addRound(currentRound)
        gameViewModel.updateGame(currentGame)

        round = null
    }

    /** Reset all the state variables, enable UIs */
    override fun onAbort(setScoreFragment: SetScoreFragment) {
        --roundsPlayed
        round = null

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_bottom,
                R.anim.slide_out_bottom,
                R.anim.slide_in_bottom,
                R.anim.slide_out_bottom
            )
            .remove(setScoreFragment)
            .commit()

        renderForegroundState()
    }

    private fun showCongratulationFragment(
        winningTeam: String,
        winningScore: Int,
        losingScore: Int
    ) {
        // Make gray background visible to simulate dialog behaviour
        renderBackgroundState()

        congratulationFragment =
            CongratulationFragment.getInstance(winningTeam, winningScore, losingScore)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_congrats, congratulationFragment!!)
            .commit()

        Log.d(TAG, "Showing congratulation fragment!")
    }

    private fun removeCongratulationFragment(congratulationFragment: CongratulationFragment) {
        supportFragmentManager
            .beginTransaction()
            .remove(congratulationFragment)
            .commit()

        renderForegroundState()
    }

    override fun onThanksButtonClicked(congratulationFragment: CongratulationFragment) {
        removeCongratulationFragment(congratulationFragment)
    }

    // Show the SetScoreDialog with focus on the team that enters its score
    private fun showSetScoreDialogForTeam(scoringTeam: Team) {
        // gray-out background & disable clickable/ focusable on control views
        renderBackgroundState()

        setScoreFragment = SetScoreFragment.getInstance(
            scoringTeam,
            binding.tvTeamname1.text.toString(),
            binding.tvTeamname2.text.toString(),
            Round(currentGame.gameId, ++roundsPlayed)
        )

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_bottom,
                R.anim.slide_out_bottom,
                R.anim.slide_in_bottom,
                R.anim.slide_out_bottom
            )
            .replace(R.id.fcv_set_score, setScoreFragment!!)
            .commit()
    }

    private fun deleteLastRound() {
        val lastRound = currentRounds[currentRounds.size - 1]
        gameViewModel.removeRoundFromGame(currentGame, lastRound)

        roundViewModel.deleteRound(lastRound)
    }

    private fun renderForegroundState() {
        binding.vGrayBackground.visibility = View.GONE
        enableInteractionViews(true)
    }

    private fun renderBackgroundState() {
        binding.vGrayBackground.visibility = View.VISIBLE
        enableInteractionViews(false)
    }

    private fun renderOverviewState() {
        with(binding) {
            vGrayBackground.visibility = View.GONE

            ibBackbutton.isEnabled = true
            ibBackbutton.isFocusable = true

            llTeam1.isEnabled = false
            llTeam2.isEnabled = false
            llTeam1.isFocusable = false
            llTeam2.isFocusable = false

            bUndo.visibility = View.GONE
        }
    }

    private fun enableInteractionViews(isEnabled: Boolean) {
        with(binding) {
            ibBackbutton.isEnabled = isEnabled
            ibBackbutton.isFocusable = isEnabled

            llTeam1.isEnabled = isEnabled
            llTeam2.isEnabled = isEnabled
            llTeam1.isFocusable = isEnabled
            llTeam2.isFocusable = isEnabled

            bUndo.isEnabled = isEnabled
            bUndo.isFocusable = isEnabled
        }
    }
}