package com.example.movieapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.MovieItemBinding

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(title: String) {
        binding.textMovieItemTitle.text = title
    }
}