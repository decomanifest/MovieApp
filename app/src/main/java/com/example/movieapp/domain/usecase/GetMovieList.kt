package com.example.movieapp.domain.usecase

import com.example.movieapp.data.ApiService
import com.example.movieapp.data.reponse.MovieEntity
import com.example.movieapp.helpers.BaseUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

// todo I'm using sort of usecase pattern but it could be also repository pattern. Anyhow this is sample app so
//  not many bells and whistles
@ViewModelScoped
class GetMovieList @Inject constructor(private val api: ApiService) :
    BaseUseCase<List<MovieEntity>, GetMovieList.Params>() {

    // todo I'm returning empty list but it's a good practice to wrap all error messages and pass them to viewModel
    //  where we can handle them accordingly
    override suspend fun action(params: Params, dispatcher: CoroutineDispatcher): List<MovieEntity> {
        return runCatching {
            val result = api.getMoviesList(title = params.title)
            // todo we should map result to domain model with help of mappers to separate logic
            result.body()?.searchResult ?: listOf()
        }.getOrElse {
            listOf()
        }
    }

    data class Params(val title: String)
}