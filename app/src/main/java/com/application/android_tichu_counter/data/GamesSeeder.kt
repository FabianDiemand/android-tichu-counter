package com.application.android_tichu_counter.data

import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds
import com.application.android_tichu_counter.domain.enums.tichu_states.TichuState.*

class GamesSeeder {

    private val meWinGame: Game = Game(
        true,
        "Unicorn",
        "Alibaba",
        1055,
        745
    )

    private fun getRounds(gameId: String): List<Round> {
        return listOf(
            Round(
                fkGameId = gameId,
                roundIndex = 1,
                firstTeamTichu = SUCCESS,
                secondTeamTichu = NA,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 135,
                secondTeamRoundScore = 65
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 2,
                firstTeamTichu = NA,
                secondTeamTichu = FAILURE,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 20,
                secondTeamRoundScore = -20
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 3,
                firstTeamTichu = NA,
                secondTeamTichu = NA,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = true,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 200,
                secondTeamRoundScore = 0
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 4,
                firstTeamTichu = NA,
                secondTeamTichu = SUCCESS,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 50,
                secondTeamRoundScore = 150
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 5,
                firstTeamTichu = NA,
                secondTeamTichu = NA,
                firstTeamGrandtichu = SUCCESS,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 230,
                secondTeamRoundScore = 70
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 6,
                firstTeamTichu = NA,
                secondTeamTichu = NA,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = true,
                firstTeamRoundScore = 0,
                secondTeamRoundScore = 200
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 7,
                firstTeamTichu = NA,
                secondTeamTichu = NA,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = SUCCESS,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 90,
                secondTeamRoundScore = 210
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 8,
                firstTeamTichu = NA,
                secondTeamTichu = NA,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 20,
                secondTeamRoundScore = 80
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 9,
                firstTeamTichu = NA,
                secondTeamTichu = NA,
                firstTeamGrandtichu = SUCCESS,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 210,
                secondTeamRoundScore = 90
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 10,
                firstTeamTichu = NA,
                secondTeamTichu = FAILURE,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 40,
                secondTeamRoundScore = -40
            ),
            Round(
                fkGameId = gameId,
                roundIndex = 11,
                firstTeamTichu = NA,
                secondTeamTichu = FAILURE,
                firstTeamGrandtichu = NA,
                secondTeamGrandtichu = NA,
                firstTeamDoubleWin = false,
                secondTeamDoubleWin = false,
                firstTeamRoundScore = 60,
                secondTeamRoundScore = -60
            )
        )
    }

    val meWin: GameWithRounds = GameWithRounds(
        game = meWinGame,
        rounds = getRounds(meWinGame.gameId)
    )
}
