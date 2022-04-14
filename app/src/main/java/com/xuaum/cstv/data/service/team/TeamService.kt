package com.xuaum.cstv.data.service.team

import com.xuaum.cstv.data.model.response.getteamsresponse.RawTeam

interface TeamService {
    suspend fun getTeams(
        team1Id: Int, team2Id: Int
    ): List<RawTeam>
}