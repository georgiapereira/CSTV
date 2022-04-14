package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.CSMatch
import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch
import kotlinx.coroutines.flow.StateFlow

interface MatchRepository {
    val getMatchesState: StateFlow<NetworkState<Nothing>>

    suspend fun getMatches(pageNumber: Int): List<CSMatch>?
}