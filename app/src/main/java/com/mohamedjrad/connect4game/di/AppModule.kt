package com.mohamedjrad.connect4game.di

import android.app.Application
import androidx.room.Room
import com.mohamedjrad.connect4game.data.db.CercleDao
import com.mohamedjrad.connect4game.data.db.LocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(app: Application)
            : LocalDatabase =
        Room.databaseBuilder(app, LocalDatabase::class.java, "local.db")
            .fallbackToDestructiveMigration()
            .createFromAsset("database/local.db")
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideCercleDao(database: LocalDatabase): CercleDao = database.cercleDao()

}
