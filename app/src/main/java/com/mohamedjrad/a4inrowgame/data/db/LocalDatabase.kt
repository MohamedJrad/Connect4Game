package com.mohamedjrad.a4inrowgame.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohamedjrad.a4inrowgame.data.model.Cercle

@Database(entities = [ Cercle::class], version = 5, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun cercleDao(): CercleDao
}