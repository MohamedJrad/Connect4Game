package com.mohamedjrad.a4inrowgame.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.mohamedjrad.a4inrowgame.data.model.Cercle

@Dao
interface CercleDao {




    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCercle(cercle: Cercle)

    @Query("UPDATE Cercle SET owner=:owner,color=:color  WHERE id=:id ")
    suspend fun updateCercle(id: String, owner: Int, color: String)

    @Query("SELECT * FROM cercle ORDER BY num DESC")
    fun getAllCerclesLive(): LiveData<List<Cercle>>

    @Query("SELECT * FROM cercle WHERE x IN (:x)  AND owner LIKE :owner")
    fun getCerclesByxLive(owner: Int, x: Int): LiveData<List<Cercle>>

    @Query("SELECT * FROM cercle WHERE y IN (:y)  AND owner LIKE :owner")
    fun getCerclesByyLive(owner: Int, y: Int): LiveData<List<Cercle>>

    @Query("SELECT * FROM cercle WHERE id LIKE :id")
    fun getCerclesByIdLive(id: String): LiveData<Cercle>


    @Query("SELECT * FROM cercle ORDER BY num DESC")
    suspend fun getAllCercles(): List<Cercle>

    @Query("SELECT * FROM cercle WHERE x IN (:x)  AND owner LIKE :owner")
    suspend fun getCerclesByx(owner: Int, x: Int): List<Cercle>

    @Query("SELECT * FROM cercle WHERE y IN (:y)  AND owner LIKE :owner")
    suspend fun getCerclesByy(owner: Int, y: Int): List<Cercle>

    @Query("SELECT * FROM cercle WHERE  owner LIKE :owner")
    suspend fun getCerclesByOwner(owner: Int): List<Cercle>

    @Query("SELECT * FROM cercle WHERE  id LIKE :id AND owner LIKE :owner")
    suspend fun getCerclesByXandY(id: String, owner: Int): Cercle

    @Query("SELECT * FROM cercle WHERE id LIKE :id")
    suspend fun getCerclesById(id: String): Cercle

    @Query("DELETE FROM cercle")
    suspend fun deleteData()
}
