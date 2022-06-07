package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.GetMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getMovieList: GetMovieList
) : ViewModel() {
    private var debounceJob: Job? = null

    private val _moviesQuery = MutableLiveData<GetMovieList.Params>()
    val movies: LiveData<PagingData<Movie>> = _moviesQuery.switchMap { requestParams ->
        Pager(
            config = PagingConfig(
                pageSize = getMovieList.pageSize,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                getMovieList.apply { params = requestParams }
            },
        ).flow.map { pagingData ->
            pagingData
        }.cachedIn(viewModelScope).asLiveData()
    }

    fun searchMovies(query: String) {
        debounceJob?.cancel()
        if (query == _moviesQuery.value?.query) {
            return
        }

        debounceJob = viewModelScope.launch {
            delay(DEBOUNCE_PERIOD)
            fetchMovies(query)
        }
    }

    private fun fetchMovies(query: String) {
        (movies as MutableLiveData).value = PagingData.empty()
        _moviesQuery.value = GetMovieList.Params(query)
    }

    companion object {
        private const val DEBOUNCE_PERIOD = 500L
    }
}
