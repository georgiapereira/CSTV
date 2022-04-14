package com.xuaum.cstv.data.repository.team

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.Team
import com.xuaum.cstv.data.model.response.toTeam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class TeamRepositoryImp @Inject constructor(private val remoteDataSource: TeamRemoteDataSource) :
    TeamRepository {
    override suspend fun getTeams(team1Id: Int, team2Id: Int): Flow<NetworkState<List<Team>>> {
        return remoteDataSource.getTeams(team1Id, team2Id)
            .map { result ->
                result.map { rawTeam -> rawTeam.toTeam() }
            }
            .map { result -> if (result.first().id == team1Id) result else result.reversed() }
            .map { result ->
                val forward: NetworkState<List<Team>> = NetworkState.Success(result)
                forward
            }
            .onStart { emit(NetworkState.Loading) }
            .catch { e -> emit(NetworkState.Failed(e as Exception)) }
    }
}