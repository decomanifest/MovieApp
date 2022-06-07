package com.example.movieapp.domain.mapper

import com.example.movieapp.data.reponse.MovieEntity
import com.example.movieapp.data.reponse.SearchEntity
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.Search

object MovieMapper {
    fun toDomain(searchResult: SearchEntity) = searchResult.toDomain()
}

private fun SearchEntity.toDomain() = Search(
    hasMore = true,
    data = searchResult.map(MovieEntity::toDomain)
)

private fun MovieEntity.toDomain() = Movie(
    imdbID = imdbID,
    title = title
)