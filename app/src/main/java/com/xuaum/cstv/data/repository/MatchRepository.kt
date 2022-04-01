package com.xuaum.cstv.data.repository

import android.util.Log
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponseItem
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import com.xuaum.cstv.ui.home.HomeFragment
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
            val result = matchAPI.getMatches(pageNumber)?.filter {
                isValidMatch(it)
            }
            _getMatchesState.value = NetworkState.Success
            result
        } catch (e: Exception) {
            _getMatchesState.value = NetworkState.Failed(e)
            null
        }
    }

    private fun isValidMatch(csMatch: CSMatch): Boolean {
        csMatch.apply {
            return opponents.size == 2 && (status == "running" || status == "not_started")

        }
    }

    suspend fun getTeams(team1Id: Int, team2Id: Int): ArrayList<GetTeamsResponseItem>? =
        withContext(Dispatchers.IO) {
            try {
                _getTeamsState.value = NetworkState.Loading
                val result = matchAPI.getTeams(team1Id, team2Id)
                if(result?.get(0)?.id == team2Id){
                    result.reverse()
                }
                _getTeamsState.value = NetworkState.Success
                result
            } catch (e: Exception) {
                _getTeamsState.value = NetworkState.Failed(e)
                null
            }
        }
}