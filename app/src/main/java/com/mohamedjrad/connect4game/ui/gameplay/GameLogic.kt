package com.mohamedjrad.connect4game.ui.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedjrad.connect4game.data.model.Cercle
import com.mohamedjrad.connect4game.data.model.Player
import com.mohamedjrad.connect4game.data.repository.AppRepositoryImp
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class GameLogic(
    val repository: AppRepositoryImp,
    var gameState: Boolean = true,
    var roundState: Boolean = true,
    var player1: Player = Player(1, 1),
    var player2: Player = Player(1, 1),
    var redPlayerScoreLive: MutableLiveData<Int> = MutableLiveData(0),
    var yellowPlayerScoreLive: MutableLiveData<Int> = MutableLiveData(0),
    var move: Int = 0,
    var roundNum: MutableLiveData<Int> = MutableLiveData(0),
    var winBy4InRow: String = "",
    var gameWinner: Int = 0,
    var roundWinner: Int = 0

) {


     fun getCerclesLive(): LiveData<List<Cercle>> = repository.getAllLive()
    suspend fun getCercles(): List<Cercle> = repository.getAll()
    suspend fun getCercleById(id: String): Cercle = repository.getCercleById(id)
    suspend fun getCercleByx(owner: Int, x: Int): List<Cercle> = repository.getCercleByx(owner, x)
    suspend fun getCercleByy(owner: Int, y: Int): List<Cercle> = repository.getCercleByy(owner, y)
    suspend fun getCerclebyXandY(id: String, owner: Int) = repository.getCerclebyXandY(id, owner)
    suspend fun getCercleByOwner(owner: Int) = repository.getCercleByOwner(owner)
    suspend fun update(id: String, owner: Int, color: String) = repository.update(id, owner, color)


    fun gameStart() {

        Timber.d("game start")
        roundStart()
    }

    fun gameComplete() {

        gameState = false

        if (redPlayerScoreLive.value!! > yellowPlayerScoreLive.value!!)
            gameWinner = 1
        else if (redPlayerScoreLive.value!! < yellowPlayerScoreLive.value!!)
            gameWinner = 2
        else
            gameWinner = 0

        Timber.d("game complete")
    }

    fun roundStart() = runBlocking {

        roundNum.postValue(roundNum.value?.plus(1))
        resetDatabase()
        Timber.d("round start")
    }

    fun roundComplete() {


        if (roundNum.value!! <= 4) {
            if (winBy4InRow.equals("Draw"))
                incrementScore()


        } else {
            if (winBy4InRow.equals("Draw"))
                incrementScore()
            gameComplete()
        }
        roundState = false

        Timber.d("round complete")
    }

    suspend fun updateCercle(id: String) {

        when (turnCounter()) {
            1 -> update(id, 1, "RED")
            2 -> update(id, 2, "YELLOW")
        }

    }

    suspend fun getMinCercleInRow(id: String): Cercle {

        val cercle = getCercleById(id)
        val unusedCerclesInSameRow = getCercleByx(cercle.owner, cercle.x)
        return minInRow(unusedCerclesInSameRow)!!


    }

    private fun minInRow(list: List<Cercle>) = list.minBy { it.y }

    private fun turnCounter(): Int {
        if (move % 2 == 0) {
            return 1
        } else
            return 2
    }

    private fun incrementScore() {
        when (turnCounter()) {
            1 -> {
                redPlayerScoreLive.postValue(
                    redPlayerScoreLive.value?.plus(1)
                )
                roundWinner = 1

            }
            2 -> {
                yellowPlayerScoreLive.postValue(
                    yellowPlayerScoreLive.value?.plus(1)
                )
                roundWinner = 2
            }
        }

    }

    fun areYConsecutive(list: List<Cercle>): Boolean {
        if (list[0].y > list[1].y) {

            if (list[0].y == list[1].y + 1 &&
                list[1].y == list[2].y + 1 &&
                list[2].y == list[3].y + 1
            )
                return true
            else return false
        } else if (list[0].y < list[1].y) {

            if (list[1].y == list[0].y + 1 &&
                list[2].y == list[1].y + 1 &&
                list[3].y == list[2].y + 1
            )
                return true
            else return false
        } else return false
    }


    fun areXConsecutive(list: List<Cercle>): Boolean {

        if (list[0].x > list[1].x) {

            if (list[0].x == list[1].x + 1 &&
                list[1].x == list[2].x + 1 &&
                list[2].x == list[3].x + 1
            )
                return true
            else return false
        } else if (list[0].x < list[1].x) {

            if (list[1].x == list[0].x + 1 &&
                list[2].x == list[1].x + 1 &&
                list[3].x == list[2].x + 1
            )
                return true
            else return false
        } else return false
    }


    suspend fun resetDatabase() {

        repository.update(id = "0,5", owner = 0, color = "null")
        repository.update(id = "1,5", owner = 0, color = "null")
        repository.update(id = "2,5", owner = 0, color = "null")
        repository.update(id = "3,5", owner = 0, color = "null")
        repository.update(id = "4,5", owner = 0, color = "null")
        repository.update(id = "5,5", owner = 0, color = "null")
        repository.update(id = "6,5", owner = 0, color = "null")


        repository.update(id = "0,4", owner = 0, color = "null")
        repository.update(id = "1,4", owner = 0, color = "null")
        repository.update(id = "2,4", owner = 0, color = "null")
        repository.update(id = "3,4", owner = 0, color = "null")
        repository.update(id = "4,4", owner = 0, color = "null")
        repository.update(id = "5,4", owner = 0, color = "null")
        repository.update(id = "6,4", owner = 0, color = "null")

        repository.update(id = "0,3", owner = 0, color = "null")
        repository.update(id = "1,3", owner = 0, color = "null")
        repository.update(id = "2,3", owner = 0, color = "null")
        repository.update(id = "3,3", owner = 0, color = "null")
        repository.update(id = "4,3", owner = 0, color = "null")
        repository.update(id = "5,3", owner = 0, color = "null")
        repository.update(id = "6,3", owner = 0, color = "null")

        repository.update(id = "0,2", owner = 0, color = "null")
        repository.update(id = "1,2", owner = 0, color = "null")
        repository.update(id = "2,2", owner = 0, color = "null")
        repository.update(id = "3,2", owner = 0, color = "null")
        repository.update(id = "4,2", owner = 0, color = "null")
        repository.update(id = "5,2", owner = 0, color = "null")
        repository.update(id = "6,2", owner = 0, color = "null")

        repository.update(id = "0,1", owner = 0, color = "null")
        repository.update(id = "1,1", owner = 0, color = "null")
        repository.update(id = "2,1", owner = 0, color = "null")
        repository.update(id = "3,1", owner = 0, color = "null")
        repository.update(id = "4,1", owner = 0, color = "null")
        repository.update(id = "5,1", owner = 0, color = "null")
        repository.update(id = "6,1", owner = 0, color = "null")

        repository.update(id = "0,0", owner = 0, color = "null")
        repository.update(id = "1,0", owner = 0, color = "null")
        repository.update(id = "2,0", owner = 0, color = "null")
        repository.update(id = "3,0", owner = 0, color = "null")
        repository.update(id = "4,0", owner = 0, color = "null")
        repository.update(id = "5,0", owner = 0, color = "null")
        repository.update(id = "6,0", owner = 0, color = "null")


    }
}


