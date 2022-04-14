package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch
import com.xuaum.cstv.data.service.match.MatchService
import javax.inject.Inject

class MatchRemoteDataSourceImp @Inject constructor(private val matchService: MatchService) :
    MatchRemoteDataSource {

    override suspend fun getMatches(pageNumber: Int): List<RawCSMatch> =
        matchService.getMatches(pageNumber = pageNumber)

}