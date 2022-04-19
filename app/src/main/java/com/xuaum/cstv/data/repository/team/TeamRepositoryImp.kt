package com.xuaum.cstv.data.repository.team

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.Team
import com.xuaum.cstv.data.model.response.toTeam
import com.xuaum.cstv.data.service.team.TeamApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TeamRepositoryImp @Inject constructor(private val teamApi: TeamApi) :
    TeamRepository {
    override suspend fun getTeams(team1Id: Int, team2Id: Int): Flow<NetworkState<List<Team>>> =
        flow {
            emit(NetworkState.Loading)

            val result = teamApi.getTeams("$team1Id,$team2Id").let { result ->
                if (result.first().id == team1Id) result else result.reversed()
            }.map { it.toTeam() }

            emit(NetworkState.Success(result))

        }.catch { e -> emit(NetworkState.Failed(e as Exception)) }
}