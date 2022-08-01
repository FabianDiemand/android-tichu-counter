package com.application.android_tichu_counter.data

import com.application.android_tichu_counter.data.entities.Game
import com.application.android_tichu_counter.data.entities.Round
import com.application.android_tichu_counter.data.entities.helper.GameWithRounds

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
                gameId,
                1,
                true,
                null,
                null,
                null,
                false,
                false,
                135,
                65
            ),
            Round(
                gameId,
                2,
                null,
                false,
                null,
                null,
                false,
                false,
                20,
                -20
            ),
            Round(
                gameId,
                3,
                null,
                null,
                null,
                null,
                true,
                false,
                200,
                0
            ),
            Round(
                gameId,
                4,
                null,
                true,
                null,
                null,
                false,
                false,
                50,
                150
            ),
            Round(
                gameId,
                5,
                null,
                null,
                true,
                null,
                false,
                false,
                230,
                70
            ),
            Round(
                gameId,
                6,
                null,
                null,
                null,
                null,
                false,
                true,
                0,
                200
            ),
            Round(
                gameId,
                7,
                null,
                null,
                null,
                true,
                false,
                false,
                90,
                210
            ),
            Round(
                gameId,
                8,
                null,
                null,
                null,
                null,
                false,
                false,
                20,
                80
            ),
            Round(
                gameId,
                9,
                null,
                null,
                true,
                null,
                false,
                false,
                210,
                90
            ),
            Round(
                gameId,
                10,
                null,
                false,
                null,
                null,
                false,
                false,
                40,
                -40
            ),
            Round(
                gameId,
                11,
                null,
                false,
                null,
                null,
                false,
                false,
                60,
                -60
            )
        )
    }

    val meWin: GameWithRounds = GameWithRounds(
        game = meWinGame,
        rounds = getRounds(meWinGame.gameId)
    )
}
