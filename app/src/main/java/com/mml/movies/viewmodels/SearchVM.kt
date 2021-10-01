package com.mml.movies.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mml.movies.repo.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    val repo: SearchRepo
) : ViewModel() {
    var searchString = mutableStateOf("")
    var searchResults = repo.searchResults

    fun search(){
        repo.onSearch(searchString.value)
        searchResults = repo.searchResults
    }
}