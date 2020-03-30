package com.mohamedjrad.a4inrowgame.ui.gameplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import com.mohamedjrad.a4inrowgame.data.model.Cercle
import com.mohamedjrad.a4inrowgame.data.model.Player


class GameLogic(
    var gameState: Boolean,
    var roundState: Boolean,
    var player1: Player,
    var player2: Player,
    var redPlayerScoreLive: MutableLiveData<Int>,
    var yellowPlayerScoreLive: MutableLiveData<Int>,
    var move: Int,
    var roundNum: MutableLiveData<Int>,
    var winBy4InRow: String,
    var gameWinner: Int,
    var roundWinner: Int

) {


    fun minInRow(list: List<Cercle>) = list.minBy { it.y }

    fun turnCounter(): Int {
        if (move % 2 == 0) {
            return 1
        } else
            return 2
    }

    fun incrementScore() {
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
}


