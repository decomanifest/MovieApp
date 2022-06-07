package com.example.movieapp.helpers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class BaseUseCase<out Type, in Params> {

    abstract suspend fun action(params: Params, dispatcher: CoroutineDispatcher): Type

    @Throws
    open suspend fun executeAction(
        params: Params,
        dispatcher: CoroutineDispatcher
    ): Type {
        return withContext(dispatcher) {
            action(params, dispatcher)
        }
    }
}