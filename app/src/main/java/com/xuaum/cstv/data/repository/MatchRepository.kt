package com.xuaum.cstv.data.repository

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.GetMatchesResponse
import com.xuaum.cstv.data.service.MatchAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.lang.Exception

class MatchRepository(private val matchAPI: MatchAPI) {
    private val _getMatchesState = MutableStateFlow<NetworkState>(NetworkState.Idle)
    val getMatchesState: Flow<NetworkState>
        get() = _getMatchesState

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
}