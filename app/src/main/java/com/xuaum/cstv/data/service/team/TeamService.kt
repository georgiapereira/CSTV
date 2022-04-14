package com.xuaum.cstv.data.service.team

import com.xuaum.cstv.data.model.response.getteamsresponse.Team

interface TeamService {
    suspend fun getTeams(
        team1Id: Int, team2Id: Int
    ): List<Team>
}