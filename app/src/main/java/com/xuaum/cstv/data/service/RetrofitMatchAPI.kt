package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponse
import com.xuaum.cstv.util.MyDateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RetrofitMatchAPI {
    private val matchAPI: MatchAPI
        get() = RetrofitAPIBuilder().buildApi(MatchAPI::class.java)

    suspend fun getMatches(): GetMatchesResponse? =
        withContext(Dispatchers.IO) {

            val currentDate = MyDateFormatter().getServerDateStringOffset(hours = -6)
            val datePlusYear = MyDateFormatter().getServerDateStringOffset(years = 1)
            val result = matchAPI.getMatches(range = "$currentDate,$datePlusYear")
            result
        }

    suspend fun getTeams(team1Id: Int, team2Id: Int): GetTeamsResponse? =
        withContext(Dispatchers.IO) {
            val result = matchAPI.getTeams("$team1Id,$team2Id")
            result
        }
}