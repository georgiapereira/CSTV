package com.xuaum.cstv.data.service.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch

interface MatchService {
    suspend fun getMatches(
        pageNumber: Int
    ): List<CSMatch>
}