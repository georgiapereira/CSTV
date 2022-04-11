package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchRepositoryImp @Inject constructor(private val remoteDataSource: MatchRemoteDataSource) :
    MatchRepository {
    private val _getMatchesState = MutableStateFlow<NetworkState<Nothing>>(NetworkState.Idle)
    override val getMatchesState: Flow<NetworkState<Nothing>>
        get() = _getMatchesState

    private val _getTeamsState = MutableStateFlow<NetworkState<List<Team>>>(NetworkState.Idle)
    override val getTeamsState: Flow<NetworkState<List<Team>>>
        get() = _getTeamsState

    override suspend fun getMatches(pageNumber: Int): List<CSMatch>? = withContext(Dispatchers.IO) {
        try {
            _getMatchesState.value = NetworkState.Loading
            val result = remoteDataSource.getMatches(pageNumber)
            _getMatchesState.value = NetworkState.Success(null)
            result
        } catch (e: Exception) {
            _getMatchesState.value = NetworkState.Failed(e)
            null
        }
    }


    override suspend fun getTeams(team1Id: Int, team2Id: Int) {
        withContext(Dispatchers.IO) {
            try {
                _getTeamsState.value = NetworkState.Loading
                _getTeamsState.value =
                    NetworkState.Success(remoteDataSource.getTeams(team1Id, team2Id))
            } catch (e: Exception) {
                _getTeamsState.value = NetworkState.Failed(e)
            }
        }
    }
}