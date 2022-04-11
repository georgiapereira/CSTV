package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

interface MatchRepository {
    val getMatchesState: Flow<NetworkState<Nothing>>

    val getTeamsState: Flow<NetworkState<List<Team>>>

    suspend fun getMatches(pageNumber: Int): List<CSMatch>?

    suspend fun getTeams(team1Id: Int, team2Id: Int)
}