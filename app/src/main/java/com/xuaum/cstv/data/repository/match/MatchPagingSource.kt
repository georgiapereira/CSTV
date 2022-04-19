package com.xuaum.cstv.data.repository.match

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xuaum.cstv.data.model.response.CSMatch
import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch
import com.xuaum.cstv.data.model.response.toCSMatch
import com.xuaum.cstv.data.service.match.MatchApi
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MatchPagingSource @Inject constructor(
    private val matchApi: MatchApi,
) : PagingSource<Int, CSMatch>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CSMatch> {
        return try {

            val pageNumber = params.key ?: 1

            val response = matchApi.getMatches(pageNumber = pageNumber).map { it.toCSMatch() }
                .filter {
                    isValidMatch(it)
                }

            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey =
                if (response.isNotEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = response,
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

    private fun isValidMatch(csMatch: CSMatch): Boolean {
        return csMatch.run {
            opponents.size == 2 && (status == "running" || status == "not_started")
        }
    }
}