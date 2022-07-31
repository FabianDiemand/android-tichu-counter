package com.application.android_tichu_counter.data

import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds
import java.util.*

class GamesSeeder {

    private val meWinGame: Game = Game(
        gameId = 1,
        updatedAt = Date(),
        createdAt = Date(),
        finished = true,
        firstTeam = "Unicorn",
        secondTeam = "Alibaba",
        firstTeamScore = 1055,
        secondTeamScore = 745
    )

    private val meWinRounds: List<Round> = listOf(
        Round(
            roundId = 1,
            gameId = 1,
            firstTeamTichuSuccess = true,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 135,
            secondTeamRoundScore = 65
        ),
        Round(
            roundId = 2,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = false,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 20,
            secondTeamRoundScore = -20
        ),
        Round(
            roundId = 3,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = true,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 200,
            secondTeamRoundScore = 0
        ),
        Round(
            roundId = 4,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = true,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 50,
            secondTeamRoundScore = 150
        ),
        Round(
            roundId = 5,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = true,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 230,
            secondTeamRoundScore = 70
        ),
        Round(
            roundId = 6,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = true,
            firstTeamRoundScore = 0,
            secondTeamRoundScore = 200
        ),
        Round(
            roundId = 7,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = true,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 90,
            secondTeamRoundScore = 210
        ),
        Round(
            roundId = 8,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 20,
            secondTeamRoundScore = 80
        ),
        Round(
            roundId = 9,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = null,
            firstTeamGrandtichuSuccess = true,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 210,
            secondTeamRoundScore = 90
        ),
        Round(
            roundId = 10,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = false,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 40,
            secondTeamRoundScore = -40
        ),
        Round(
            roundId = 11,
            gameId = 1,
            firstTeamTichuSuccess = null,
            secondTeamTichuSuccess = false,
            firstTeamGrandtichuSuccess = null,
            secondTeamGrandtichuSuccess = null,
            firstTeamDoubleWin = false,
            secondTeamDoubleWin = false,
            firstTeamRoundScore = 60,
            secondTeamRoundScore = -60
        )
    )

    val meWin: GameWithRounds = GameWithRounds(
        game = meWinGame,
        rounds = meWinRounds
    )
}
