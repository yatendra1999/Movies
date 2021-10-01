package com.mml.movies.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mml.movies.network.models.Movies

@Database(entities = [
    Movies::class,
    Stats::class
], version = 2)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}