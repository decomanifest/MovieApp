package com.example.movieapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.adapter.MovieAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.helpers.paging.LoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
        setListeners()
        setObservers()
    }

    private fun initLayout() {
        binding.recyclerMainActivityMoviesList.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)
        // todo we can easy add more custom states like empty state, error state, etc.
        binding.recyclerMainActivityMoviesList.adapter = movieAdapter.withLoadStateFooter(LoadStateAdapter())
    }

    private fun setObservers() {
        viewModel.movies.observe(this) {
            movieAdapter.submitData(lifecycle, it)
        }
    }

    private fun setListeners() {
        binding.searchMainActivityView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchMovies(newText)
                return true
            }
        })
    }

    companion object {
        private const val GRID_SPAN_COUNT = 2
    }
}