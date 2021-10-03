package com.mml.movies.viewmodels;

import androidx.lifecycle.ViewModel;
import com.mml.movies.network.models.Movies;
import com.mml.movies.repo.ActiveRepo;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;

@HiltViewModel
public class ActiveVM extends ViewModel{
    ActiveRepo repo;
    @Inject ActiveVM(
    ActiveRepo repo){
        this.repo = repo;
        active = repo.getActive();
        updateData();
    }

    public Observable<List<Movies>> active;
    public String locale = null;

    void updateData() {
        repo.updateData(locale,1, 1);
    }
}