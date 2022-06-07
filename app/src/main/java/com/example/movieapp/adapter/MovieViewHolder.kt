package com.example.movieapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.data.reponse.MovieEntity
import com.example.movieapp.databinding.MovieItemBinding

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieEntity) {
        binding.textMovieItemTitle.text = movie.title
    }
}