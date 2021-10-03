package com.mml.movies.room;

import androidx.room.*;
import com.mml.movies.network.models.Movies;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM Movies")
    Observable<List<Movies>> fetchAllMovies();
    @Query("SELECT * FROM Stats")
    Observable<List<Stats>> fetchAllStats();

    @Query("SELECT * FROM Movies WHERE id IN (:idList)")
    Single<List<Movies>> fetchMovieByIds(List<Integer> idList);

    @Query("SELECT * FROM Movies WHERE id = :id")
    Single<Movies> fetchSingleMovie(int id);
    @Query("SELECT * FROM Stats WHERE id = :id")
    Single<Stats> fetchSingleStat(int id);

    @Query("SELECT * FROM Movies WHERE title LIKE :name")
    Observable<List<Movies>> fetchByName(String name);

    @Query("SELECT Movies.* FROM Movies INNER JOIN Stats ON Movies.id = Stats.id WHERE bookmarked = 1")
    Observable<List<Movies>> getBookmarked();
    @Query("UPDATE Stats SET bookmarked = 1 WHERE id = :id")
    Completable addBookMark(int id);
    @Query("UPDATE Stats SET bookmarked = 0 WHERE id = :id")
    Completable removeBookMark(int id);

    @Query("SELECT Movies.* FROM Movies INNER JOIN Stats ON Movies.id = Stats.id WHERE trending = 1")
    Observable<List<Movies>> getTrending();
    @Query("UPDATE Stats SET trending = 0")
    Completable removeAllTrending();
    @Query("UPDATE Stats SET trending = 1 WHERE id = :id")
    Completable setTrending(int id);

    @Query("SELECT Movies.* FROM Movies INNER JOIN Stats ON Movies.id = Stats.id WHERE active = 1")
    Observable<List<Movies>> getActive();
    @Query("UPDATE Stats SET active = 0")
    Completable removeAllActive();
    @Query("UPDATE Stats SET active = 1 WHERE id = :id")
    Completable setActive(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMovies(Movies ...movies);
    @Insert(onConflict = OnConflictStrategy.ABORT)
    Completable insertStats(Stats ...stats);

    @Update
    Completable updateStat(Stats stat);
    @Update
    Completable updateMovie(Movies movie);


    @Delete
    Completable deleteStat(Stats stat);
    @Delete
    Completable deleteMovie(Movies movie);
}
