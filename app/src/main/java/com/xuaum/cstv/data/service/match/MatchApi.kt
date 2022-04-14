package com.xuaum.cstv.data.service.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApi{
    @GET("/csgo/matches")
    suspend fun getMatches(
        @Query("sort") sortBegin: String = "begin_at",
        @Query("page[size]") pageSize: Int = 40,
        @Query("page[number]") pageNumber: Int,
        @Query("range[status]") status: String = "not_started,running"
    ): List<CSMatch>

}