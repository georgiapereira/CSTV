package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import kotlinx.coroutines.flow.Flow

interface MatchRemoteDataSource {
    suspend fun getMatches(pageNumber: Int): List<CSMatch>
}