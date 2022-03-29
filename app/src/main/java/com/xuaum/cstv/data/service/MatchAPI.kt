package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.model.response.GetMatchesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchAPI {
    @GET("/matches")
    suspend fun getMatches(
        @Query("sort") sort: String = "begin_at",
        @Query("page[size]") pageSize: Int = 20,
        @Query("range[scheduled_at]") range: String = "2022-03-29T12:00:00Z,2023-01-13T12:00:00Z"
    ): GetMatchesResponse?
}