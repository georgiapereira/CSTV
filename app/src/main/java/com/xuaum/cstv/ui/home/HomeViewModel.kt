package com.xuaum.cstv.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.xuaum.cstv.data.repository.match.MatchPagingSourceFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchPagingSourceFactory: MatchPagingSourceFactory
) : ViewModel() {

    val getMatchesPager = Pager(
        PagingConfig(pageSize = 15, prefetchDistance = 10, initialLoadSize = 20)
    ) {
        matchPagingSourceFactory.create()
    }.flow
        .cachedIn(viewModelScope)

}