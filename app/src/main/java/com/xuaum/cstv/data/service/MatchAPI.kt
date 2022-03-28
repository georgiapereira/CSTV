package com.xuaum.cstv.data.service

import com.xuaum.cstv.data.model.response.GetMatchesResponse
import retrofit2.http.GET

interface MatchAPI {
    @GET("/matches")
    suspend fun getMatches(): GetMatchesResponse?
}