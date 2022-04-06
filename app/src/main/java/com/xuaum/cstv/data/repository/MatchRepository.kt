package com.xuaum.cstv.data.repository

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class MatchRepository(private val matchAPI: RetrofitMatchAPI) {
    private val _getMatchesState = MutableStateFlow<NetworkState<Nothing>>(NetworkState.Idle)
    val getMatchesState: Flow<NetworkState<Nothing>>
        get() = _getMatchesState

    private val _getTeamsState = MutableStateFlow<NetworkState<List<Team>>>(NetworkState.Idle)
    val getTeamsState: Flow<NetworkState<List<Team>>>
        get() = _getTeamsState

    suspend fun getMatches(pageNumber: Int): List<CSMatch>? = withContext(Dispatchers.IO) {
        try {
            _getMatchesState.value = NetworkState.Loading
            val result = matchAPI.getMatches(pageNumber)
            _getMatchesState.value = NetworkState.Success()
            result
        } catch (e: Exception) {
            _getMatchesState.value = NetworkState.Failed(e)
            null
        }
    }


    suspend fun getTeams(team1Id: Int, team2Id: Int) {
        withContext(Dispatchers.IO) {
            try {
                _getTeamsState.value = NetworkState.Loading
                _getTeamsState.value = NetworkState.Success(matchAPI.getTeams(team1Id, team2Id))
            } catch (e: Exception) {
                _getTeamsState.value = NetworkState.Failed(e)
            }
        }
    }
}