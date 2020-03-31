package com.mohamedjrad.connect4game.data.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cercle(
    @PrimaryKey()
    val id: String,
    val num:Int,
    var owner:Int=0,
    var color: String,
    val x: Int,
    val y: Int

)