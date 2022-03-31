package com.xuaum.cstv.data.repository

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponse
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.lang.Exception

class MatchRepository(private val matchAPI: RetrofitMatchAPI) {
    private val _getMatchesState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val getMatchesState: Flow<NetworkState>
        get() = _getMatchesState

    private val _getTeamsState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val getTeamsState: Flow<NetworkState>
        get() = _getTeamsState

    suspend fun getMatches(): GetMatchesResponse? = withContext(Dispatchers.IO) {
        try {
            _getMatchesState.value = NetworkState.Loading
            val result = matchAPI.getMatches()
            _getMatchesState.value = NetworkState.Success
            result
        } catch (e: Exception) {
            _getMatchesState.value = NetworkState.Failed(e)
            null
        }
    }

    suspend fun getTeams(team1Id: Int, team2Id: Int): GetTeamsResponse? =
        withContext(Dispatchers.IO) {
            try {
                _getTeamsState.value = NetworkState.Loading
                val result = matchAPI.getTeams(team1Id, team2Id)
                _getTeamsState.value = NetworkState.Success
                result
            } catch (e: Exception) {
                _getTeamsState.value = NetworkState.Failed(e)
                null
            }
        }
}