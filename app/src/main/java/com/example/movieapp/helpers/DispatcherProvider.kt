package com.example.movieapp.helpers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

@Singleton
class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider {
    override val main get() = Dispatchers.Main
    override val default get() = Dispatchers.Default
    override val io get() = Dispatchers.IO
    override val unconfined get() = Dispatchers.Unconfined
}