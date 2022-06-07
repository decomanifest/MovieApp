package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.reponse.MovieEntity
import com.example.movieapp.domain.usecase.GetMovieList
import com.example.movieapp.helpers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getMovieList: GetMovieList,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _moviesList: MutableLiveData<List<MovieEntity>> = MutableLiveData()
    val moviesList: LiveData<List<MovieEntity>> = _moviesList

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _moviesList.value = listOf()
            return
        }

        fetchMovies(query)
    }

    private fun fetchMovies(query: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            runCatching {
                val result = getMovieList.executeAction(GetMovieList.Params(query), dispatcherProvider.io)
                _moviesList.value = result
            }.onFailure { Timber.d("Fetching error ${it.message}") }
        }
    }
}
