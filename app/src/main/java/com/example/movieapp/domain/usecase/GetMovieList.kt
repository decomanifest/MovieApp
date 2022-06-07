package com.example.movieapp.domain.usecase

import com.example.movieapp.data.ApiService
import com.example.movieapp.domain.mapper.MovieMapper
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.Search
import com.example.movieapp.helpers.paging.PaginatedResponse
import com.example.movieapp.helpers.paging.PaginationUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

// todo I'm using usecase pattern but it could be also repository pattern. Anyhow this is sample app so
//  not many bells and whistles
@ViewModelScoped
class GetMovieList @Inject constructor(private val api: ApiService) :
    PaginationUseCase<Movie>() {

    override val startPageIndex: Int = PAGE_INDEX
    override val pageSize: Int = PAGE_SIZE

    lateinit var params: Params

    // todo I'm returning empty list but it's a good practice to wrap all error messages and pass them to viewModel
    //  where we can handle them accordingly
    override suspend fun getPaginatedData(page: Int): PaginatedResponse<Movie> {
        return runCatching {
            val result = api.getMoviesList(page = page, params.query)
            val nonNullResult = result.body() ?: return@runCatching Search(false, listOf())

            MovieMapper.toDomain(nonNullResult)
        }.getOrElse {
            it.printStackTrace()
            Search(false, listOf())
        }
    }

    data class Params(val query: String)

    companion object {
        private const val PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
    }
}
