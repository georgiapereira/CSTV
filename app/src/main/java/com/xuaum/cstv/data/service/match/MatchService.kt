package com.xuaum.cstv.data.service.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch

interface MatchService {
    suspend fun getMatches(
        pageNumber: Int
    ): List<RawCSMatch>
}