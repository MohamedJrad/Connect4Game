package com.mohamedjrad.a4inrowgame.ui.gameplay

import androidx.lifecycle.*
import com.mohamedjrad.a4inrowgame.data.model.Cercle
import com.mohamedjrad.a4inrowgame.data.model.Player
import com.mohamedjrad.a4inrowgame.data.repository.AppRepositoryImp
import kotlinx.coroutines.*
import javax.inject.Inject

class GamePlayViewModel @Inject constructor(val repository: AppRepositoryImp) :
    ViewModel() {

    lateinit var gameLogic: GameLogic


    init {
        gameStart()
    }

    fun gameStart() {

        gameLogic = GameLogic(
            true, true, Player(1, 1), Player(2, 2), MutableLiveData(0),
            MutableLiveData(0), 0, MutableLiveData(0), "", 0, 0
        )
        gameLogic.gameState = true
        gameLogic.roundState = true
        gameLogic.player1 = Player(1, 1)
        gameLogic.player2 = Player(2, 2)
        gameLogic.redPlayerScoreLive = MutableLiveData(0)
        gameLogic.yellowPlayerScoreLive = MutableLiveData(0)
        gameLogic.move = 0
        gameLogic.roundNum = MutableLiveData(0)
        gameLogic.winBy4InRow = ""
        gameLogic.roundWinner = 0
        gameLogic.gameWinner = 0

        roundStart()
    }

    fun gameComplete() {

        gameLogic.gameState = false

        if (gameLogic.redPlayerScoreLive.value!! > gameLogic.yellowPlayerScoreLive.value!!)
            gameLogic.gameWinner = 1
        else if (gameLogic.redPlayerScoreLive.value!! < gameLogic.yellowPlayerScoreLive.value!!)
            gameLogic.gameWinner = 2
        else
            gameLogic.gameWinner = 0


    }

    fun roundStart() = runBlocking {

        gameLogic.roundNum.postValue(gameLogic.roundNum.value?.plus(1))
        resetDatabase()

    }

    fun roundComplete() {


        if (gameLogic.roundNum.value!! <= 4) {
            if (!gameLogic.winBy4InRow.equals("Draw"))
                gameLogic.incrementScore()


        } else {
            if (!gameLogic.winBy4InRow.equals("Draw"))
                gameLogic.incrementScore()
            gameComplete()
        }
        gameLogic.roundState = false
    }


    fun cercleClicked(id: String) = runBlocking {


        val minCercleInRow = async { getMinCercleInRow(id) }.await()


        val first = async {
            if (minCercleInRow.owner == 0)
                updateCercle(minCercleInRow.id)
        }.await()


        val init = async {

            if (gameLogic.roundState) {

                checkGameStateVertical(minCercleInRow.id)
            }
        }.await()

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



        gameLogic.move++
    }

    suspend fun getMinCercleInRow(id: String): Cercle {

        val cercle = getCercleById(id)
        val unusedCerclesInSameRow = getCercleByx(cercle.owner, cercle.x)
        return gameLogic.minInRow(unusedCerclesInSameRow)!!


    }

    suspend fun updateCercle(id: String) {

        when (gameLogic.turnCounter()) {
            1 -> repository.update(id, 1, "RED")
            2 -> repository.update(id, 2, "YELLOW")
        }


    }


    override fun onCleared() = runBlocking {
        super.onCleared()
        gameLogic.roundState = true
        resetDatabase()

    }


    suspend fun checkGameStateVertical(id: String) {

        val cercle = getCercleById(id)

        if (cercle.owner != 0)
            if (getCercleByx(cercle.owner, cercle.x).size == 4) {
                if (gameLogic.areYConsecutive(
                        getCercleByx(
                            cercle.owner,
                            cercle.x
                        ).sortedBy { cercle.y })
                ) {
                    for (item in getCercleByx(cercle.owner, cercle.x)) {

                        repository.update(item.id, 0, "PURPLE")


                    }
                    gameLogic.winBy4InRow = "Vertical"
                    roundComplete()

                }
            }

    }

    suspend fun checkGameStateHorizontal(id: String) {

        val cercle = getCercleById(id)
        if (cercle.owner != 0)
            if (getCercleByy(cercle.owner, cercle.y).size == 4) {
                if (gameLogic.areXConsecutive(
                        getCercleByy(
                            cercle.owner,
                            cercle.y
                        ).sortedBy { cercle.x })
                ) {
                    for (item in getCercleByy(cercle.owner, cercle.y)) {

                        repository.update(item.id, 0, "PURPLE")


                    }
                    gameLogic.winBy4InRow = "Horizontal"
                    roundComplete()

                }

            }


    }

    suspend fun checkGameStateDiagonal1(id: String) {
        val cercle = getCercleById(id)

        val list2: MutableList<Cercle> = mutableListOf()
        var cercle2 = getCerclebyXandY(cercle.id, cercle.owner)

        var x: Int = cercle.x
        var y: Int = cercle.y

        while (x <= 6 && y <= 5) {

            cercle2 = getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)


            x++
            y++
        }

        x = cercle.x - 1
        y = cercle.y - 1

        while (x >= 0 && y >= 0) {

            val cercle2 = getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)

            x--
            y--
        }


        if (list2.size == 4) {
            if (gameLogic.areXConsecutive(list2.sortedBy { it.x })) {
                for (item in list2) {

                    repository.update(item.id, 0, "PURPLE")


                }
                gameLogic.winBy4InRow = "Diagonal"
                roundComplete()

            }

        }

    }

    suspend fun checkGameStateDiagonal2(id: String) {

        val cercle = getCercleById(id)
        val list2: MutableList<Cercle> = mutableListOf()
        var cercle2 = getCerclebyXandY(cercle.id, cercle.owner)

        var x: Int = cercle.x
        var y: Int = cercle.y

        while (x <= 6 && y >= 0) {

            val cercle2 = getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)


            x++
            y--
        }
        x = cercle.x - 1
        y = cercle.y + 1

        while (x >= 0 && y <= 5) {

            val cercle2 = getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)

            x--
            y++
        }

        if (list2.size == 4) {
            if (gameLogic.areXConsecutive(list2.sortedBy { it.x })) {
                for (item in list2) {

                    repository.update(item.id, 0, "PURPLE")


                }
                gameLogic.winBy4InRow = "Diagonal"
                roundComplete()

            }

        }
    }

    suspend fun checkIfDrow() {
        if (getCercleByOwner(0).size == 0) {
            gameLogic.winBy4InRow = "Draw"
            roundComplete()


        }
    }


    fun getCerclesLive(): LiveData<List<Cercle>> = repository.getAllLive()
    fun getCercleByIdLive(id: String): LiveData<Cercle> = repository.getCercleByIdLive(id)
    fun getCercleByxLive(owner: Int, x: Int): LiveData<List<Cercle>> =
        repository.getCercleByxLive(owner, x)

    suspend fun getCercles(): List<Cercle> = repository.getAll()
    suspend fun getCercleById(id: String): Cercle = repository.getCercleById(id)
    suspend fun getCercleByx(owner: Int, x: Int): List<Cercle> = repository.getCercleByx(owner, x)
    suspend fun getCercleByy(owner: Int, y: Int): List<Cercle> = repository.getCercleByy(owner, y)
    suspend fun deleteData() = repository.deleteData()
    suspend fun getCerclebyXandY(id: String, owner: Int) = repository.getCerclebyXandY(id, owner)
    suspend fun getCercleByOwner(owner: Int) = repository.getCercleByOwner(owner)
    suspend fun update(id: String, owner: Int, color: String) = repository.update(id, owner, color)
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

    suspend fun prepopulateDatabae() {


        repository.insert(Cercle(id = "0,5", num = 42, color = "null", x = 0, y = 5))
        repository.insert(Cercle(id = "1,5", num = 41, color = "null", x = 1, y = 5))
        repository.insert(Cercle(id = "2,5", num = 40, color = "null", x = 2, y = 5))
        repository.insert(Cercle(id = "3,5", num = 39, color = "null", x = 3, y = 5))
        repository.insert(Cercle(id = "4,5", num = 38, color = "null", x = 4, y = 5))
        repository.insert(Cercle(id = "5,5", num = 37, color = "null", x = 5, y = 5))
        repository.insert(Cercle(id = "6,5", num = 36, color = "null", x = 6, y = 5))


        repository.insert(Cercle(id = "0,4", num = 35, color = "null", x = 0, y = 4))
        repository.insert(Cercle(id = "1,4", num = 34, color = "null", x = 1, y = 4))
        repository.insert(Cercle(id = "2,4", num = 33, color = "null", x = 2, y = 4))
        repository.insert(Cercle(id = "3,4", num = 32, color = "null", x = 3, y = 4))
        repository.insert(Cercle(id = "4,4", num = 31, color = "null", x = 4, y = 4))
        repository.insert(Cercle(id = "5,4", num = 30, color = "null", x = 5, y = 4))
        repository.insert(Cercle(id = "6,4", num = 29, color = "null", x = 6, y = 4))

        repository.insert(Cercle(id = "0,3", num = 28, color = "null", x = 0, y = 3))
        repository.insert(Cercle(id = "1,3", num = 27, color = "null", x = 1, y = 3))
        repository.insert(Cercle(id = "2,3", num = 26, color = "null", x = 2, y = 3))
        repository.insert(Cercle(id = "3,3", num = 25, color = "null", x = 3, y = 3))
        repository.insert(Cercle(id = "4,3", num = 24, color = "null", x = 4, y = 3))
        repository.insert(Cercle(id = "5,3", num = 23, color = "null", x = 5, y = 3))
        repository.insert(Cercle(id = "6,3", num = 22, color = "null", x = 6, y = 3))

        repository.insert(Cercle(id = "0,2", num = 21, color = "null", x = 0, y = 2))
        repository.insert(Cercle(id = "1,2", num = 20, color = "null", x = 1, y = 2))
        repository.insert(Cercle(id = "2,2", num = 19, color = "null", x = 2, y = 2))
        repository.insert(Cercle(id = "3,2", num = 18, color = "null", x = 3, y = 2))
        repository.insert(Cercle(id = "4,2", num = 17, color = "null", x = 4, y = 2))
        repository.insert(Cercle(id = "5,2", num = 16, color = "null", x = 5, y = 2))
        repository.insert(Cercle(id = "6,2", num = 15, color = "null", x = 6, y = 2))

        repository.insert(Cercle(id = "0,1", num = 14, color = "null", x = 0, y = 1))
        repository.insert(Cercle(id = "1,1", num = 13, color = "null", x = 1, y = 1))
        repository.insert(Cercle(id = "2,1", num = 12, color = "null", x = 2, y = 1))
        repository.insert(Cercle(id = "3,1", num = 11, color = "null", x = 3, y = 1))
        repository.insert(Cercle(id = "4,1", num = 10, color = "null", x = 4, y = 1))
        repository.insert(Cercle(id = "5,1", num = 9, color = "null", x = 5, y = 1))
        repository.insert(Cercle(id = "6,1", num = 8, color = "null", x = 6, y = 1))

        repository.insert(Cercle(id = "0,0", num = 7, color = "null", x = 0, y = 0))
        repository.insert(Cercle(id = "1,0", num = 6, color = "null", x = 1, y = 0))
        repository.insert(Cercle(id = "2,0", num = 5, color = "null", x = 2, y = 0))
        repository.insert(Cercle(id = "3,0", num = 4, color = "null", x = 3, y = 0))
        repository.insert(Cercle(id = "4,0", num = 3, color = "null", x = 4, y = 0))
        repository.insert(Cercle(id = "5,0", num = 2, color = "null", x = 5, y = 0))
        repository.insert(Cercle(id = "6,0", num = 1, color = "null", x = 6, y = 0))


    }
}

