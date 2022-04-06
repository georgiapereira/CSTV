package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.util.MyDateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RetrofitMatchAPI {
    private val matchAPI: MatchAPI
        get() = RetrofitAPIBuilder().buildApi(MatchAPI::class.java)

    suspend fun getMatches(pageNumber: Int): List<CSMatch> =
        withContext(Dispatchers.IO) {
            val result = matchAPI.getMatches(pageNumber = pageNumber)
            result
        }

    suspend fun getTeams(team1Id: Int, team2Id: Int): List<Team> =
        withContext(Dispatchers.IO) {
            val result = matchAPI.getTeams("$team1Id,$team2Id")
            result
        }
}