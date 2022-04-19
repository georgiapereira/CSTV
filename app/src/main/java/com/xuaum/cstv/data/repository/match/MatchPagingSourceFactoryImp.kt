package com.xuaum.cstv.data.repository.match

import androidx.paging.PagingSource
import com.xuaum.cstv.data.model.response.CSMatch
import com.xuaum.cstv.data.service.match.MatchApi
import javax.inject.Inject

class MatchPagingSourceFactoryImp @Inject constructor(private val matchApi: MatchApi): MatchPagingSourceFactory {
    override fun create(): PagingSource<Int, CSMatch> {
        return MatchPagingSource(matchApi)
    }
}