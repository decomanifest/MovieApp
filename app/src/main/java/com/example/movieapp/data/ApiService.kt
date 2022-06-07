package com.example.movieapp.data

import com.example.movieapp.data.reponse.SearchEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    suspend fun getMoviesList(
        @Query("page") page: Int,
        @Query("s") title: String,
        @Query("type") type: String = "movie"
    ): Response<SearchEntity>
}