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
    private val _getMatchesState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val getMatchesState: Flow<NetworkState>
        get() = _getMatchesState

    private val _getTeamsState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val getTeamsState: Flow<NetworkState>
        get() = _getTeamsState

    suspend fun getMatches(pageNumber: Int): List<CSMatch>? = withContext(Dispatchers.IO) {
        try {
            _getMatchesState.value = NetworkState.Loading
            matchAPI.getMatches(pageNumber).also {
                _getMatchesState.value = NetworkState.Success
            }
        } catch (e: Exception) {
            _getMatchesState.value = NetworkState.Failed(e)
            null
        }
    }

    suspend fun getTeams(team1Id: Int, team2Id: Int): List<Team>? =
        withContext(Dispatchers.IO) {
            try {
                _getTeamsState.value = NetworkState.Loading
                matchAPI.getTeams(team1Id, team2Id).also {
                    _getTeamsState.value = NetworkState.Success
                }
            } catch (e: Exception) {
                _getTeamsState.value = NetworkState.Failed(e)
                null
            }
        }
}