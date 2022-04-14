package com.xuaum.cstv.data.repository.team

import com.xuaum.cstv.data.model.response.getteamsresponse.RawTeam
import kotlinx.coroutines.flow.Flow

interface TeamRemoteDataSource {
    suspend fun getTeams(team1Id: Int, team2Id: Int): Flow<List<RawTeam>>
}