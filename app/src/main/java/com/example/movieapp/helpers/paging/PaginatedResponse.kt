package com.example.movieapp.helpers.paging

interface PaginatedResponse<T> {
    val hasMore: Boolean
    val data: List<T>
}
