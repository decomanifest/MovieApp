package com.example.movieapp.helpers.paging

import androidx.core.view.isInvisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.LoadingStateItemBinding

class LoadStateViewHolder(private val binding: LoadingStateItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        val isLoading = loadState is LoadState.Loading
        binding.textLoadingStateItem.isInvisible = isLoading.not()
    }
}
