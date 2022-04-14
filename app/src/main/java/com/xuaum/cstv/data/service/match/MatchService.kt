package com.xuaum.cstv.data.service.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchService {
    suspend fun getMatches(
        pageNumber: Int
    ): List<CSMatch>
}