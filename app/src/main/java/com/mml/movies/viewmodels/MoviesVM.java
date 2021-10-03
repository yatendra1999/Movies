package com.mml.movies.viewmodels;


import androidx.lifecycle.ViewModel;
import com.mml.movies.network.models.Movies;
import com.mml.movies.room.MovieDao;
import java.util.List;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

@HiltViewModel
public class MoviesVM extends ViewModel{

    @Inject MoviesVM(MovieDao movieDao){
        this.movieDao = movieDao;
        allMovies = movieDao.fetchAllMovies();
        bookmarked = movieDao.getBookmarked();
    }

    MovieDao movieDao;
    public Observable<List<Movies>> bookmarked;
    public Observable<List<Movies>> allMovies;

    public void addBookmark(int id){
        movieDao.addBookMark(id).subscribeOn(Schedulers.io()).subscribe();
    }

    public void removeBookmark(int id){
        movieDao.removeBookMark(id).subscribeOn(Schedulers.io()).subscribe();
    }
}