package com.mohamedjrad.connect4game.ui.gameplay

import androidx.lifecycle.*
import com.mohamedjrad.connect4game.data.model.Cercle
import com.mohamedjrad.connect4game.data.model.Player
import com.mohamedjrad.connect4game.data.repository.AppRepositoryImp
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class GamePlayViewModel @Inject constructor(private val repository: AppRepositoryImp) :
    ViewModel() {

     val gameLogic = GameLogic(repository = repository)
    private val connectFourChecker = ConnectFourChecker(gameLogic)


    init {
        gameLogic.gameStart()
    }


    fun cercleClicked(id: String) = runBlocking {


        val minCercleInRow = gameLogic.getMinCercleInRow(id)

        if (minCercleInRow.owner == 0)
            gameLogic.updateCercle(minCercleInRow.id)

        if (gameLogic.roundState)
            connectFourChecker.checkGameStateVertical(minCercleInRow.id)

        if (gameLogic.roundState)
            connectFourChecker.checkGameStateHorizontal(minCercleInRow.id)


        if (gameLogic.roundState)
            connectFourChecker.checkGameStateDiagonal1(minCercleInRow.id)

        if (gameLogic.roundState)
            connectFourChecker.checkGameStateDiagonal2(minCercleInRow.id)

        if (gameLogic.roundState)
            connectFourChecker.checkIfDraw()


/*
        val minCercleInRow = async { getMinCercleInRow(id) }.await()


        val first = async {
            if (minCercleInRow.owner == 0)
                updateCercle(minCercleInRow.id)
        }.await()

/*
        val init = async {

            if (gameLogic.roundState) {

                checkGameStateVertical(minCercleInRow.id)
            }
        }.await()


 */
        val second = async {
            if (gameLogic.roundState) {

                checkGameStateVertical(minCercleInRow.id)
            }

        }.await()

        val third = async {
            if (gameLogic.roundState) {

                checkGameStateHorizontal(minCercleInRow.id)
            }
        }.await()


        val fourth = async {

            if (gameLogic.roundState) {

                checkGameStateDiagonal1(minCercleInRow.id)

            }
        }.await()

        val fifeth = async {
            if (gameLogic.roundState) {

                checkGameStateDiagonal2(minCercleInRow.id)

            }
        }.await()

        val sixth = async {
            if (gameLogic.roundState) {
                checkIfDrow()

            }
        }.await()




 */
        gameLogic.move++
    }

    override fun onCleared() = runBlocking {
        super.onCleared()
        gameLogic.roundState = true
        gameLogic.resetDatabase()

    }


}
