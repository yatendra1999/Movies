package com.mml.movies.room

import androidx.room.*
import com.mml.movies.network.models.Movies
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movies")
    fun fetchAllMovies(): Observable<List<Movies>>
    @Query("SELECT * FROM Stats")
    fun fetchAllStats(): Observable<List<Stats>>

    @Query("SELECT * FROM Movies WHERE id IN (:idList)")
    fun fetchMovieByIds(idList: List<Int>): Single<List<Movies>>

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun fetchSingleMovie(id: Int): Single<Movies>
    @Query("SELECT * FROM Stats WHERE id = :id")
    fun fetchSingleStat(id: Int): Single<Stats>

    @Query("SELECT * FROM Movies WHERE title LIKE :name")
    fun fetchByName(name: String) : Observable<List<Movies>>

    @Query("SELECT Movies.* FROM Movies INNER JOIN Stats ON Movies.id = Stats.id WHERE bookmarked = 1")
    fun getBookmarked(): Observable<List<Movies>>
    @Query("UPDATE Stats SET bookmarked = 1 WHERE id = :id")
    fun addBookMark(id: Int): Completable
    @Query("UPDATE Stats SET bookmarked = 0 WHERE id = :id")
    fun removeBookMark(id: Int): Completable

    @Query("SELECT Movies.* FROM Movies INNER JOIN Stats ON Movies.id = Stats.id WHERE trending = 1")
    fun getTrending(): Observable<List<Movies>>
    @Query("UPDATE Stats SET trending = 0")
    fun removeAllTrending(): Completable
    @Query("UPDATE Stats SET trending = 1 WHERE id = :id")
    fun setTrending(id:Int): Completable

    @Query("SELECT Movies.* FROM Movies INNER JOIN Stats ON Movies.id = Stats.id WHERE active = 1")
    fun getActive(): Observable<List<Movies>>
    @Query("UPDATE Stats SET active = 0")
    fun removeAllActive(): Completable
    @Query("UPDATE Stats SET active = 1 WHERE id = :id")
    fun setActive(id:Int): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movies: Movies): Completable
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertStats(vararg stats: Stats): Completable

    @Update
    fun updateStat(stat: Stats): Completable
    @Update
    fun updateMovie(movie: Movies): Completable


    @Delete
    fun deleteStat(stat: Stats): Completable
    @Delete
    fun deleteMovie(movie: Movies): Completable
}
