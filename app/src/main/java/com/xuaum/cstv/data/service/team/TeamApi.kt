package com.xuaum.cstv.data.service.team

import com.xuaum.cstv.data.model.response.getteamsresponse.RawTeam
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamApi {
    @GET("/csgo/teams")
    suspend fun getTeams(
        @Query("filter[id]") ids: String
    ): List<RawTeam>
}