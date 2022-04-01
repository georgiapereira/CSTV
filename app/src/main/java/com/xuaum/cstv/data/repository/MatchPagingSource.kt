package com.xuaum.cstv.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import retrofit2.HttpException
import java.io.IOException

class MatchPagingSource(
    private val matchRepository: MatchRepository,
) : PagingSource<Int, CSMatch>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CSMatch> {
        return try {

            val pageNumber = params.key ?: 1

            val response = matchRepository.getMatches(pageNumber)

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey =
                if (response?.isNotEmpty() == true) pageNumber + 1 else null

            LoadResult.Page(
                data = response ?: listOf(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CSMatch>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}