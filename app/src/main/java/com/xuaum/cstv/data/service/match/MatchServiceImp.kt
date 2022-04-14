package com.xuaum.cstv.data.service.match

import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import javax.inject.Inject

class MatchServiceImp @Inject constructor(private val matchApi: MatchApi) : MatchService {
    override suspend fun getMatches(pageNumber: Int): List<CSMatch> {
        return matchApi.getMatches(pageNumber = pageNumber)
    }
}