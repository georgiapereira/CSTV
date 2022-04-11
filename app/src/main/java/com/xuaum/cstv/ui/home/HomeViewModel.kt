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
import com.xuaum.cstv.data.repository.match.MatchPagingSource
import com.xuaum.cstv.data.repository.match.MatchRepository
import com.xuaum.cstv.data.repository.match.MatchRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {
    private val _getMatchesState = MutableLiveData<NetworkState<Nothing>>(NetworkState.Idle)
    val getMatchesState: LiveData<NetworkState<Nothing>>
        get() = _getMatchesState

    val getMatchesPager = Pager(
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