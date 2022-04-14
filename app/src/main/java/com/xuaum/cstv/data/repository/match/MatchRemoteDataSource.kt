package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch

interface MatchRemoteDataSource {
    suspend fun getMatches(pageNumber: Int): List<CSMatch>
}