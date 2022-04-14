package com.xuaum.cstv.data.service.team

import com.xuaum.cstv.data.model.response.getteamsresponse.RawTeam
import javax.inject.Inject

class TeamServiceImp @Inject constructor(private val teamApi: TeamApi): TeamService {
    override suspend fun getTeams(team1Id: Int, team2Id: Int): List<RawTeam> {
        return teamApi.getTeams("$team1Id,$team2Id")
    }
}