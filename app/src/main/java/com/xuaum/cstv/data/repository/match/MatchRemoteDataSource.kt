package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch

interface MatchRemoteDataSource {
    suspend fun getMatches(pageNumber: Int): List<RawCSMatch>
}