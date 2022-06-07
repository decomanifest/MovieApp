package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val _moviesList: MutableLiveData<List<String>> = MutableLiveData()
    val moviesList: LiveData<List<String>> = _moviesList

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _moviesList.value = listOf()
            return
        }

        _moviesList.value = List(20) { query }
    }
}