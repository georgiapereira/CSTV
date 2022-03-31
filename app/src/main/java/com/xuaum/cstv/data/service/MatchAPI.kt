package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchAPI {
    @GET("/csgo/matches")
    suspend fun getMatches(
        @Query("sort") sortBegin: String = "begin_at",
        @Query("sort") sortStatus: String = "-status",
        @Query("page[size]") pageSize: Int = 20,
        @Query("range[scheduled_at]") range: String = "2022-03-29T12:00:00Z,2023-01-13T12:00:00Z",
        @Query("filter[finished]") finished: Boolean = false,
    ): GetMatchesResponse?

    @GET("/csgo/teams")
    suspend fun getTeams(
        @Query("filter[id]") ids: String
    ): GetTeamsResponse?
}