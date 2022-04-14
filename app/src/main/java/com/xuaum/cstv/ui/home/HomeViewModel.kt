package com.xuaum.cstv.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.repository.match.MatchPagingSource
import com.xuaum.cstv.data.repository.match.MatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {
    private val _getMatchesState = MutableStateFlow<NetworkState<Nothing>>(NetworkState.Idle)
    val getMatchesState: StateFlow<NetworkState<Nothing>>
        get() = _getMatchesState

    val getMatchesPager = Pager(
        PagingConfig(pageSize = 40)
    ) {
        MatchPagingSource(matchRepository)
    }.flow
        .cachedIn(viewModelScope)

    init {
        setupGetMatchesStateListener()
    }

    private fun setupGetMatchesStateListener() {
        viewModelScope.launch {
            matchRepository.getMatchesState.collectLatest { state ->
                _getMatchesState.value = state
            }
        }
    }

}