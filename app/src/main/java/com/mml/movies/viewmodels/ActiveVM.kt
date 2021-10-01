package com.mml.movies.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mml.movies.network.ApiService
import com.mml.movies.network.Constants
import com.mml.movies.network.models.Movies
import com.mml.movies.repo.ActiveRepo
import com.mml.movies.room.Stats
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ActiveVM @Inject constructor(
    val repo: ActiveRepo
) : ViewModel() {
    var active = repo.getActive()

    init {
        updateData()
    }

    fun updateData() {
        repo.updateData()
    }
}