package com.example.movieapp.domain.model

import com.example.movieapp.helpers.paging.PaginatedResponse

data class Search(
    override val hasMore: Boolean,
    override val data: List<Movie>
) : PaginatedResponse<Movie>