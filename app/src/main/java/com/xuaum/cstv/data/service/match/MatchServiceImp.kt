package com.xuaum.cstv.data.service.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import javax.inject.Inject

class MatchServiceImp @Inject constructor(private val matchAPI: MatchAPI) : MatchService {
    override suspend fun getMatches(pageNumber: Int): List<CSMatch> {
        return matchAPI.getMatches(pageNumber = pageNumber)
    }

    override suspend fun getTeams(team1Id: Int, team2Id: Int): List<Team> {
        return matchAPI.getTeams("$team1Id,$team2Id")
    }
}