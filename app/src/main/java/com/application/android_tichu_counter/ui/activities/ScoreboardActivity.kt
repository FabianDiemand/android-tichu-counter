package com.application.android_tichu_counter.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.application.android_tichu_counter.BaseActivity
import com.application.android_tichu_counter.R
import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.viewmodel.GameViewModel
import com.application.android_tichu_counter.data.viewmodel.RoundViewModel
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
class ScoreboardActivity : BaseActivity(), SetScoreFragment.SetScoreListener, CongratulationFragment.CongratulationListener {
    companion object {
        private const val TAG = "ScoreboardActivity"

        const val KEY_GAME_ID = "Game_Id"
        const val KEY_TEAM_1 = "Team_1"
        const val KEY_TEAM_2 = "Team_2"
    }

    // Important ui components
    private lateinit var ibBackbutton: ImageButton

    private lateinit var vGrayBackground: View

    private lateinit var llFirstTeamScoreboard: LinearLayout
    private lateinit var llSecondTeamScoreboard: LinearLayout

    private lateinit var tvFirstTeamName: TextView
    private lateinit var tvSecondTeamName: TextView

    private lateinit var tvFirstTeamScore: TextView
    private lateinit var tvSecondTeamScore: TextView

    private lateinit var tlFirstTeamRounds: TableLayout
    private lateinit var tlSecondTeamRounds: TableLayout

    private lateinit var thFirstTeamRounds: TableRow
    private lateinit var thSecondTeamRounds: TableRow

    private lateinit var fcvScoreFragmentContainer: FragmentContainerView

    private val gameViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameViewModel::class.java]
    }

    private val roundViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RoundViewModel::class.java]
    }

    // Activity variables
    private var setScoreFragment: SetScoreFragment? = null
    private var congratulationFragment: CongratulationFragment? = null

    private var scoringTeamId: Int = -1

    private var currentGameId: String = ""
    private lateinit var currentGame: Game
    private var round: Round? = null
    private var roundsPlayed: Int = 0

    /** Create view, instantiate ui components, set listeners. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        ibBackbutton = findViewById(R.id.ib_backbutton)

        tvFirstTeamName = findViewById(R.id.tv_teamname1)
        tvSecondTeamName = findViewById(R.id.tv_teamname2)

        tvFirstTeamScore = findViewById(R.id.tv_score1)
        tvSecondTeamScore = findViewById(R.id.tv_score2)

        tlFirstTeamRounds = findViewById(R.id.tl_rounds_team1)
        tlSecondTeamRounds = findViewById(R.id.tl_rounds_team2)

        thFirstTeamRounds = findViewById(R.id.tr_header_team1)
        thSecondTeamRounds = findViewById(R.id.tr_header_team2)

        llFirstTeamScoreboard = findViewById(R.id.ll_team1)
        llSecondTeamScoreboard = findViewById(R.id.ll_team2)

        fcvScoreFragmentContainer = findViewById(R.id.fcv_set_score)
        vGrayBackground = findViewById(R.id.gray_background)

        initializeUi()
        setListeners()

        Log.d(TAG, "Create view.")
    }

    // Set listeners to important ui components
    private fun setListeners() {
        ibBackbutton.setOnClickListener {
            onBackPressed()
        }

        llFirstTeamScoreboard.setOnClickListener {
            showSetScoreDialog(tvFirstTeamName.text.toString(), tvSecondTeamName.text.toString())
            scoringTeamId = it.id
        }

        llSecondTeamScoreboard.setOnClickListener {
            showSetScoreDialog(tvSecondTeamName.text.toString(), tvFirstTeamName.text.toString())
            scoringTeamId = it.id
        }

        vGrayBackground.setOnClickListener {
            onRemoveClicked(setScoreFragment!!)
        }

        Log.d(TAG, "Set OnClickListeners")
    }

    // Initialize the scores and the team names
    private fun initializeUi() {
        processIntent()

        thFirstTeamRounds.visibility = View.GONE
        thSecondTeamRounds.visibility = View.GONE

        tvFirstTeamScore.text = "0"
        tvSecondTeamScore.text = "0"
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
                val rounds = gameWithRounds.rounds
                roundsPlayed = rounds.size

                tvFirstTeamName.text = currentGame.firstTeam
                tvFirstTeamScore.text = currentGame.firstTeamScore.toString()

                tvSecondTeamName.text = currentGame.secondTeam
                tvSecondTeamScore.text = currentGame.secondTeamScore.toString()

                if(currentGame.finished){
                    disableClickableViews()
                } else {
                    enableClickableViews()
                }

                if(rounds.isNotEmpty()){
                    tlFirstTeamRounds.removeAllViews()
                    tlSecondTeamRounds.removeAllViews()
                    thFirstTeamRounds.visibility = View.VISIBLE
                    thSecondTeamRounds.visibility = View.VISIBLE
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

                    tlFirstTeamRounds.addView(trTeam1)
                    tlSecondTeamRounds.addView(trTeam2)
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
        vGrayBackground.visibility = View.GONE

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
            llFirstTeamScoreboard.id -> round?.setFirstTeamDoubleWin()
            llSecondTeamScoreboard.id -> round?.setSecondTeamDoubleWin()
        }

        roundViewModel.addRound(round!!)

        currentGame.firstTeamScore += round?.calculateFirstTeamScore(0)!!
        currentGame.secondTeamScore += round?.calculateSecondTeamScore(0)!!
        gameViewModel.updateGame(currentGame)

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)

        evaluateWin()
    }

    /** Calculate the new score on a normal round (no double win) */
    override fun onOkClicked(setScoreFragment: SetScoreFragment, value: Int) {
        var firstTeamRoundScore = 0
        var secondTeamRoundScore = 0

        when (scoringTeamId) {
            llFirstTeamScoreboard.id -> {
                firstTeamRoundScore = round?.calculateFirstTeamScore(value)!!
                secondTeamRoundScore = round?.calculateSecondTeamScore(100 - value)!!
            }
            llSecondTeamScoreboard.id -> {
                secondTeamRoundScore = round?.calculateSecondTeamScore(value)!!
                firstTeamRoundScore = round?.calculateFirstTeamScore(100 - value)!!
            }
        }

        roundViewModel.addRound(round!!)

        currentGame.firstTeamScore += firstTeamRoundScore
        currentGame.secondTeamScore += secondTeamRoundScore
        gameViewModel.updateGame(currentGame)

        // Remove the fragment and reset round state variables
        onRemoveClicked(setScoreFragment)

        evaluateWin()
    }

    private fun evaluateWin(){
        with(currentGame){
            if(firstTeamScore >= 1000 && secondTeamScore >= 1000){
                if(firstTeamScore > secondTeamScore){
                    showCongratulationFragment(tvFirstTeamName.text.toString(),firstTeamScore, secondTeamScore )
                } else if(secondTeamScore > firstTeamScore){
                    showCongratulationFragment(tvSecondTeamName.text.toString(),secondTeamScore, firstTeamScore )
                }
            } else if(firstTeamScore >= 1000){
                showCongratulationFragment(tvFirstTeamName.text.toString(),firstTeamScore, secondTeamScore )
            } else if(secondTeamScore >= 1000){
                showCongratulationFragment(tvSecondTeamName.text.toString(),secondTeamScore, firstTeamScore )
            }
        }
    }

    /** Reset all the state variables, enable UIs */
    override fun onRemoveClicked(setScoreFragment: SetScoreFragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
            .remove(setScoreFragment)
            .commit()

        supportFragmentManager.popBackStack()

        round = null
        vGrayBackground.visibility = View.GONE
        flipComponentsEnabled()
    }

    private fun showCongratulationFragment(winningTeam: String, winningScore: Int, losingScore: Int){
        congratulationFragment = CongratulationFragment.getInstance(winningTeam, winningScore, losingScore)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_congrats, congratulationFragment!!)
            .addToBackStack(congratulationFragment!!.tag)
            .commit()

        // Make gray background visible to simulate dialog behaviour
        vGrayBackground.visibility = View.VISIBLE
        // Disable views in the background
        flipComponentsEnabled()

        Log.d(TAG, "Showing congratulation fragment!")
    }

    private fun removeCongratulationFragment(congratulationFragment: CongratulationFragment){
        supportFragmentManager
            .beginTransaction()
            .remove(congratulationFragment)
            .commit()

        supportFragmentManager.popBackStack()

        vGrayBackground.visibility = View.GONE
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
        vGrayBackground.visibility = View.VISIBLE
        // Disable views in the background
        flipComponentsEnabled()

        round = Round(currentGame.gameId, ++roundsPlayed)
    }

    // Flip enabled state of layout key components
    private fun flipComponentsEnabled() {
        ibBackbutton.isEnabled = !ibBackbutton.isEnabled
        llFirstTeamScoreboard.isEnabled = !llFirstTeamScoreboard.isEnabled
        llSecondTeamScoreboard.isEnabled = !llSecondTeamScoreboard.isEnabled
    }

    private fun disableClickableViews(){
        llFirstTeamScoreboard.isEnabled = false
        llSecondTeamScoreboard.isEnabled = false
        llFirstTeamScoreboard.isFocusable = false
        llSecondTeamScoreboard.isFocusable = false
    }

    private fun enableClickableViews(){
        llFirstTeamScoreboard.isEnabled = true
        llSecondTeamScoreboard.isEnabled = true
        llFirstTeamScoreboard.isFocusable = false
        llSecondTeamScoreboard.isFocusable = false
    }
}