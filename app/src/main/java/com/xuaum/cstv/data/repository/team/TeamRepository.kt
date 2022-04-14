package com.xuaum.cstv.data.repository.team

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.Team
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    suspend fun getTeams(team1Id: Int, team2Id: Int): Flow<NetworkState<List<Team>>>
}