package com.mml.movies.hilt

import android.content.Context
import androidx.room.Room
import com.mml.movies.room.MovieDao
import com.mml.movies.room.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): MovieDataBase{
        return Room.databaseBuilder(
            context,
            MovieDataBase::class.java,
            "MovieDB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(movieDB: MovieDataBase): MovieDao{
        return movieDB.movieDao()
    }
}