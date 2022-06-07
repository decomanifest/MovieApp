package com.example.movieapp.data.reponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// todo I will use Entity class directly but in real life example would be better to separate request/response
// classes from data layer from mapped to domain classes used in the app business logic
@JsonClass(generateAdapter = true)
data class MovieEntity(
    val imdbID: String,
    @Json(name = "Title") val title: String
)
