package com.mml.movies.viewmodels;

import androidx.lifecycle.ViewModel;
import com.mml.movies.network.models.Movies;
import com.mml.movies.repo.TrendingRepo;
import java.util.List;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;
import javax.inject.Inject;

@HiltViewModel
public class TrendingVM extends ViewModel{

    @Inject TrendingVM(TrendingRepo repo){
        this.repo = repo;
        trendingMovies = repo.getTrending();
        updateData();
    }

    TrendingRepo repo;
    public Observable<List<Movies>> trendingMovies;

    public void updateData(){
        repo.updateData(1, 1);
    }
}