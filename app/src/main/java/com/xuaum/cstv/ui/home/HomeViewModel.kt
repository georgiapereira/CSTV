package com.xuaum.cstv.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.repository.MatchPagingSource
import com.xuaum.cstv.data.repository.MatchRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {
    private val _getMatchesState = MutableLiveData<NetworkState>(NetworkState.Idle)
    val getMatchesState: LiveData<NetworkState>
        get() = _getMatchesState

    val getMatchesPagingResponse = Pager(
        PagingConfig(pageSize = 40)
    ) {
        MatchPagingSource(matchRepository)
    }.liveData
        .cachedIn(viewModelScope)

    init {
        setupGetMatchesStateListener()
    }

    private fun setupGetMatchesStateListener() {
        viewModelScope.launch {
            matchRepository.getMatchesState.collect { state ->
                _getMatchesState.postValue(state)
            }
        }
    }

}