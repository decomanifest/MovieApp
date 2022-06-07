package com.example.movieapp.helpers.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class PaginationUseCase<Source : Any> : PagingSource<Int, Source>() {
    abstract suspend fun getPaginatedData(page: Int): PaginatedResponse<Source>
    abstract val startPageIndex: Int
    abstract val pageSize: Int

    override fun getRefreshKey(state: PagingState<Int, Source>): Int? = state.anchorPosition
        ?.let(state::closestPageToPosition)?.let { it.prevKey?.plus(1) ?: it.nextKey?.minus(1) }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Source> {
        val page = params.key ?: startPageIndex
        return runCatching {
            val response = getPaginatedData(page)
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == startPageIndex) null else page - 1,
                nextKey = if (response.hasMore) page + 1 else null
            )
        }.getOrElse {
            LoadResult.Error(Exception(it))
        }
    }
}
