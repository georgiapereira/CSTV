package com.xuaum.cstv.data.repository.match

import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.CSMatch
import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch
import com.xuaum.cstv.data.model.response.toCSMatch
import com.xuaum.cstv.data.service.match.MatchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchRepositoryImp @Inject constructor(private val matchApi: MatchApi) :
    MatchRepository {
    private val _getMatchesState = MutableStateFlow<NetworkState<Nothing>>(NetworkState.Idle)
    override val getMatchesState: StateFlow<NetworkState<Nothing>>
        get() = _getMatchesState

    override suspend fun getMatches(pageNumber: Int): List<CSMatch>? = withContext(Dispatchers.IO) {
        try {
            _getMatchesState.value = NetworkState.Loading
            val result = matchApi.getMatches(pageNumber = pageNumber).map { it.toCSMatch() }
            _getMatchesState.value = NetworkState.Success(null)
            result
        } catch (e: Exception) {
            _getMatchesState.value = NetworkState.Failed(e)
            null
        }
    }

}