package com.mohamedjrad.a4inrowgame.data.repository

import androidx.lifecycle.LiveData
import com.mohamedjrad.a4inrowgame.data.db.CercleDao
import com.mohamedjrad.a4inrowgame.data.model.Cercle
import javax.inject.Inject

class AppRepositoryImp @Inject constructor(
    private val cercleDao: CercleDao
) : AppRepository {


    override suspend fun insert(cercle: Cercle) = cercleDao.insertCercle(cercle)


    override suspend fun update(id: String, owner: Int, color: String) =
        cercleDao.updateCercle(id, owner, color)


    override fun getAllLive(): LiveData<List<Cercle>> = cercleDao.getAllCerclesLive()


    override fun getCercleByxLive(owner: Int, x: Int): LiveData<List<Cercle>> =
        cercleDao.getCerclesByxLive(owner, x)

    override fun getCercleByIdLive(id: String): LiveData<Cercle> =
        cercleDao.getCerclesByIdLive(id)

    override suspend fun getAll(): List<Cercle> = cercleDao.getAllCercles()

    override suspend fun getCercleByx(owner: Int, x: Int): List<Cercle> =
        cercleDao.getCerclesByx(owner, x)


    override suspend fun getCercleById(id: String): Cercle = cercleDao.getCerclesById(id)

    override suspend fun deleteData() = cercleDao.deleteData()


    override fun getCercleByyLive(owner: Int, y: Int): LiveData<List<Cercle>> =
        cercleDao.getCerclesByyLive(owner, y)

    override suspend fun getCercleByy(owner: Int, y: Int): List<Cercle> =
        cercleDao.getCerclesByy(owner, y)

    override suspend fun getCerclebyXandY(id: String, owner: Int): Cercle =
        cercleDao.getCerclesByXandY(id, owner)

    override suspend fun getCercleByOwner(owner: Int): List<Cercle> =
        cercleDao.getCerclesByOwner(owner)

}