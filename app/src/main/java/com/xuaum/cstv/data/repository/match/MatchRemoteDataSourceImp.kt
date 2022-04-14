package com.xuaum.cstv.data.repository.match

import android.util.Log
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.data.service.match.MatchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MatchRemoteDataSourceImp @Inject constructor(private val matchService: MatchService) :
    MatchRemoteDataSource {

    override suspend fun getMatches(pageNumber: Int): List<CSMatch> =
        matchService.getMatches(pageNumber = pageNumber)

}