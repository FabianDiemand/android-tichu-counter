package io.github.devtronaut.android_tichu_counter.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import io.github.devtronaut.android_tichu_counter.R
import io.github.devtronaut.android_tichu_counter.app.TichuApplication
import io.github.devtronaut.android_tichu_counter.data.entities.Game
import io.github.devtronaut.android_tichu_counter.data.entities.Round
import io.github.devtronaut.android_tichu_counter.data.entities.TeamRound
import io.github.devtronaut.android_tichu_counter.data.viewmodel.GameViewModel
import io.github.devtronaut.android_tichu_counter.data.viewmodel.RoundViewModel
import io.github.devtronaut.android_tichu_counter.databinding.ActivityScoreboardBinding
import io.github.devtronaut.android_tichu_counter.domain.enums.teams.Team
import io.github.devtronaut.android_tichu_counter.domain.enums.teams.Team.FIRST_TEAM
import io.github.devtronaut.android_tichu_counter.domain.enums.teams.Team.SECOND_TEAM
import io.github.devtronaut.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_1
import io.github.devtronaut.android_tichu_counter.ui.activities.ScoreboardActivity.Companion.KEY_TEAM_2
import io.github.devtronaut.android_tichu_counter.ui.fragments.CongratulationFragment
import io.github.devtronaut.android_tichu_counter.ui.fragments.RoundProgressFragment
import io.github.devtronaut.android_tichu_counter.ui.fragments.SetScoreFragment
import kotlinx.coroutines.delay
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

    private var setScoreFragment: SetScoreFragment? = null

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
            val firstName =
                if (intent.hasExtra(KEY_TEAM_1)) intent.getStringExtra(KEY_TEAM_1) else getString(R.string.default_teamname_1)

            val secondName =
                if (intent.hasExtra(KEY_TEAM_2)) intent.getStringExtra(KEY_TEAM_2) else getString(R.string.default_teamname_2)

            currentGame = Game(firstName!!, secondName!!)
            currentGameId = currentGame.gameId
            gameViewModel.addGame(currentGame)
        }

        observeGameWithRounds(currentGameId)
    }

    @SuppressLint("InflateParams")
    private fun observeGameWithRounds(gameId: String) {
        lifecycleScope.launch {

            delay(100)

            gameViewModel.getGameWithRounds(gameId).collect { gameWithRounds ->
                currentGame = gameWithRounds.game
                currentRounds = gameWithRounds.rounds
                val rounds = gameWithRounds.rounds
                roundsPlayed = rounds.size

                with(binding) {
                    tvTeamname1.text = currentGame.firstTeam
                    tvScore1.text = currentGame.firstTeamScore.toString()

                    tvTeamname2.text = currentGame.secondTeam
                    tvScore2.text = currentGame.secondTeamScore.toString()

                    bUndo.visibility = if (rounds.isNotEmpty()) View.VISIBLE else View.GONE
                }

                if (currentGame.finished) {
                    renderOverviewState()
                    Log.d(TAG, "Game is finished, interactive views disabled.")
                } else {
                    renderForegroundState()
                }

                val firstTeamRounds: ArrayList<TeamRound> = ArrayList()
                val secondTeamRounds: ArrayList<TeamRound> = ArrayList()

                rounds.asReversed().forEach { round ->
                    with(round) {
                        firstTeamRounds.add(
                            TeamRound(
                                firstTeamTichu,
                                firstTeamGrandtichu,
                                firstTeamDoubleWin,
                                firstTeamRoundScore
                            )
                        )

                        secondTeamRounds.add(
                            TeamRound(
                                secondTeamTichu,
                                secondTeamGrandtichu,
                                secondTeamDoubleWin,
                                secondTeamRoundScore
                            )
                        )
                    }
                }

                with(RoundProgressFragment) {
                    val firstTeamGameProgress = getInstance(firstTeamRounds)
                    val secondTeamGameProgress = getInstance(secondTeamRounds)

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fcv_game_progress_team1, firstTeamGameProgress)
                        .replace(R.id.fcv_game_progress_team2, secondTeamGameProgress)
                        .commit()
                }
            }
        }
    }

    /** Calculate the new score on a normal round (no double win) */
    override fun onReturnWithResult(setScoreFragment: SetScoreFragment, currentRound: Round) {
        onAbort(setScoreFragment)
        finishRound(currentRound)
    }

    private fun finishRound(currentRound: Round) {
        with(currentGame) {
            addFirstTeamScore(currentRound.firstTeamRoundScore)
            addSecondTeamScore(currentRound.secondTeamRoundScore)

            if (finished) {
                showCongratulationFragment(getWinningTeamName(), firstTeamScore, secondTeamScore)
            }
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
                R.anim.slide_in_bottom, R.anim.slide_out_bottom,
                R.anim.slide_in_bottom, R.anim.slide_out_bottom
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

        val congratulationFragment =
            CongratulationFragment.getInstance(winningTeam, winningScore, losingScore)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_congrats, congratulationFragment)
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
                R.anim.slide_in_bottom, R.anim.slide_out_bottom,
                R.anim.slide_in_bottom, R.anim.slide_out_bottom
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
            ibBackbutton.isEnabled = true
            ibBackbutton.isFocusable = true

            llTeam1.setOnClickListener(null)
            llTeam2.setOnClickListener(null)

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