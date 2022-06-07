package com.example.movieapp.data.reponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchEntity(@Json(name = "Search") val searchResult: List<MovieEntity>)
