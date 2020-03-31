package com.mohamedjrad.connect4game.data.repository

import androidx.lifecycle.LiveData
import com.mohamedjrad.connect4game.data.model.Cercle

interface AppRepository {


    suspend fun insert(cercle: Cercle)

    suspend fun update(id: String, owner: Int, color: String)

    fun getAllLive(): LiveData<List<Cercle>>

    fun getCercleByxLive(owner: Int, x: Int): LiveData<List<Cercle>>

    fun getCercleByyLive(owner: Int, y: Int): LiveData<List<Cercle>>

    fun getCercleByIdLive(id: String): LiveData<Cercle>

    suspend fun getAll(): List<Cercle>

    suspend fun getCercleByx(owner: Int, x: Int): List<Cercle>

    suspend fun getCercleByy(owner: Int, y: Int): List<Cercle>

    suspend fun getCercleByOwner(owner: Int): List<Cercle>

    suspend fun getCerclebyXandY(id: String, owner: Int): Cercle

    suspend fun getCercleById(id: String): Cercle

    suspend fun deleteData()
}