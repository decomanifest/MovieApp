package com.example.movieapp.data.di

import com.example.movieapp.data.ApiService
import com.example.movieapp.data.BASE_URL
import com.example.movieapp.helpers.DefaultDispatcherProvider
import com.example.movieapp.helpers.DispatcherProvider
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MoviesApiModule {

    @Binds
    fun bindDispatcherProvider(dispatcherProvider: DefaultDispatcherProvider): DispatcherProvider

    companion object {
        @Singleton
        @Provides
        fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        @Singleton
        @Provides
        fun provideApiKeyInterceptor(): Interceptor =
            Interceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl: HttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    // todo in real life example we should not expose api keys like here
                    .addQueryParameter("apikey", "b9bd48a6")
                    .build()
                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }

        @Singleton
        @Provides
        fun providesOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            apiKeyInterceptor: Interceptor
        ): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(apiKeyInterceptor)
                .build()

        @Provides
        @Singleton
        fun providesMoviesApi(
            okHttpClient: OkHttpClient,
            moshi: Moshi
        ): Retrofit =
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder().build()

        @Singleton
        @Provides
        fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
    }
}
