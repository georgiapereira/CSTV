package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team

interface MatchRemoteDataSource {
    suspend fun getMatches(pageNumber: Int): List<CSMatch>

    suspend fun getTeams(team1Id: Int, team2Id: Int): List<Team>
}