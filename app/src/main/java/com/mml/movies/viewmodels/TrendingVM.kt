package com.mml.movies.viewmodels

import androidx.lifecycle.ViewModel
import com.mml.movies.repo.TrendingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingVM @Inject constructor(
    val repo: TrendingRepo
) : ViewModel() {

    var trendingMovies = repo.getTrending()

    init {
        updateData()
    }

    fun updateData(){
        repo.updateData()
    }
}