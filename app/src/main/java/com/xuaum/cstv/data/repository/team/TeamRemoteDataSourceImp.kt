package com.xuaum.cstv.data.repository.team

import android.util.Log
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.data.service.team.TeamService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TeamRemoteDataSourceImp @Inject constructor(private val teamService: TeamService) :
    TeamRemoteDataSource {
    override suspend fun getTeams(team1Id: Int, team2Id: Int): Flow<List<Team>> = flow {
        Log.i("aaaaa", "getTeams: aaaa")
        val result = teamService.getTeams(team1Id, team2Id)
        Log.i("aaaaa", "getTeams: aaaa")
        emit(result)
    }
}