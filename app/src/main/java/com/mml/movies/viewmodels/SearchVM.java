package com.mml.movies.viewmodels;

import androidx.lifecycle.ViewModel;

import com.mml.movies.network.models.Movies;
import com.mml.movies.repo.SearchRepo;

import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;

import javax.inject.Inject;

@HiltViewModel
public class SearchVM extends ViewModel{
    @Inject SearchVM(SearchRepo repo){
        this.repo = repo;
        searchResults = repo.searchResults;
    }

    SearchRepo repo;
    public String searchString = "";
    public Observable<List<Movies>> searchResults;

    public void search(String searchString){
        repo.onSearch(searchString);
        searchResults = repo.searchResults;
    }
}