package com.mohamedjrad.connect4game.ui.gameplay

import com.mohamedjrad.connect4game.data.model.Cercle
import com.mohamedjrad.connect4game.data.repository.AppRepositoryImp
import timber.log.Timber

class ConnectFourChecker(val gameLogic: GameLogic) {




    suspend fun checkGameStateVertical(id: String) {

        val cercle = gameLogic.getCercleById(id)

        if (cercle.owner != 0)
            if (gameLogic.getCercleByx(cercle.owner, cercle.x).size == 4) {
                if (gameLogic.areYConsecutive(
                        gameLogic.getCercleByx(
                            cercle.owner,
                            cercle.x
                        ).sortedBy { cercle.y })
                ) {
                    for (item in gameLogic.getCercleByx(cercle.owner, cercle.x)) {

                        gameLogic.update(item.id, 0, "PURPLE")


                    }
                    gameLogic.winBy4InRow = "Vertical"
                    gameLogic.roundComplete()

                }
            }
        Timber.d("Vertical")
    }

    suspend fun checkGameStateHorizontal(id: String) {

        val cercle = gameLogic.getCercleById(id)
        if (cercle.owner != 0)
            if (gameLogic.getCercleByy(cercle.owner, cercle.y).size == 4) {
                if (gameLogic.areXConsecutive(
                        gameLogic.getCercleByy(
                            cercle.owner,
                            cercle.y
                        ).sortedBy { cercle.x })
                ) {
                    for (item in gameLogic.getCercleByy(cercle.owner, cercle.y)) {

                        gameLogic.update(item.id, 0, "PURPLE")


                    }
                    gameLogic.winBy4InRow = "Horizontal"
                    gameLogic.roundComplete()

                }

            }
        Timber.d("Horizontal")

    }

    suspend fun checkGameStateDiagonal1(id: String) {
        val cercle = gameLogic.getCercleById(id)

        val list2: MutableList<Cercle> = mutableListOf()
        var cercle2 = gameLogic.getCerclebyXandY(cercle.id, cercle.owner)

        var x: Int = cercle.x
        var y: Int = cercle.y

        while (x <= 6 && y <= 5) {

            cercle2 = gameLogic.getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)


            x++
            y++
        }

        x = cercle.x - 1
        y = cercle.y - 1

        while (x >= 0 && y >= 0) {

            val cercle2 = gameLogic.getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)

            x--
            y--
        }


        if (list2.size == 4) {
            if (gameLogic.areXConsecutive(list2.sortedBy { it.x })) {
                for (item in list2) {

                    gameLogic.update(item.id, 0, "PURPLE")


                }
                gameLogic.winBy4InRow = "Diagonal"
                gameLogic.roundComplete()

            }

        }
        Timber.d("Diagonal1")
    }

    suspend fun checkGameStateDiagonal2(id: String) {

        val cercle = gameLogic.getCercleById(id)
        val list2: MutableList<Cercle> = mutableListOf()
        var cercle2 = gameLogic.getCerclebyXandY(cercle.id, cercle.owner)

        var x: Int = cercle.x
        var y: Int = cercle.y

        while (x <= 6 && y >= 0) {

            val cercle2 = gameLogic.getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)


            x++
            y--
        }
        x = cercle.x - 1
        y = cercle.y + 1

        while (x >= 0 && y <= 5) {

            val cercle2 = gameLogic.getCerclebyXandY("" + x + "," + y, cercle.owner)
            if (cercle2 != null)
                list2.add(cercle2)

            x--
            y++
        }

        if (list2.size == 4) {
            if (gameLogic.areXConsecutive(list2.sortedBy { it.x })) {
                for (item in list2) {

                    gameLogic.update(item.id, 0, "PURPLE")


                }
                gameLogic.winBy4InRow = "Diagonal"
                gameLogic.roundComplete()

            }

        }

        Timber.d("Diagonal2")
    }

    suspend fun checkIfDraw() {
        if (gameLogic.getCercleByOwner(0).size == 0) {
            gameLogic.winBy4InRow = "Draw"
            gameLogic.roundComplete()

        }

        Timber.d("Draw")
    }

}