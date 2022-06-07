package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.movieapp.data.reponse.MovieEntity
import com.example.movieapp.databinding.MovieItemBinding

class MovieAdapter : ListAdapter<MovieEntity, MovieViewHolder>(ContentDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentDiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem.imdbID == newItem.imdbID

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean =
            oldItem.title == newItem.title
    }
}