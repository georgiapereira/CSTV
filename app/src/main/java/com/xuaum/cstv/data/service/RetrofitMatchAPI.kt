package com.xuaum.cstv.data.service

import android.util.Log
import com.xuaum.cstv.data.model.response.GetMatchesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RetrofitMatchAPI: MatchAPI {
    private val matchAPI: MatchAPI
        get() = RetrofitAPIBuilder().buildApi(MatchAPI::class.java)

    override suspend fun getMatches(sortBegin: String, sortStatus: String, pageSize: Int, range: String, finished: Boolean): GetMatchesResponse? = withContext(Dispatchers.IO) {
        val result = matchAPI.getMatches()
        result
    }
}