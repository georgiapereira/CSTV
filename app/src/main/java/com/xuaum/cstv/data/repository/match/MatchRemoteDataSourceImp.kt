package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.data.service.match.MatchAPI
import com.xuaum.cstv.data.service.match.MatchService
import javax.inject.Inject

class MatchRemoteDataSourceImp @Inject constructor(private val matchService: MatchService) :
    MatchRemoteDataSource {
    override suspend fun getMatches(pageNumber: Int): List<CSMatch> =
        matchService.getMatches(pageNumber = pageNumber)

    override suspend fun getTeams(team1Id: Int, team2Id: Int): List<Team> =
        matchService.getTeams(team1Id, team2Id)
}