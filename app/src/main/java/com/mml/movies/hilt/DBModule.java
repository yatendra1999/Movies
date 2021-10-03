package com.mml.movies.hilt;

import android.content.Context;
import androidx.room.Room;
import com.mml.movies.room.MovieDao;
import com.mml.movies.room.MovieDataBase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DBModule {

    @Provides
    @Singleton
    public static MovieDataBase provideDataBase(@ApplicationContext Context context){
        return Room.databaseBuilder(
            context,
            MovieDataBase.class,
            "MovieDB"
        ).build();
    }

    @Provides
    @Singleton
    public static MovieDao provideMoviesDao(MovieDataBase movieDB){
        return movieDB.movieDao();
    }
}