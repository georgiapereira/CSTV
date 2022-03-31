package com.xuaum.cstv.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.GetMatchesResponse
import com.xuaum.cstv.data.model.response.getteamsresponse.GetTeamsResponse
import com.xuaum.cstv.data.repository.MatchRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val matchRepository: MatchRepository
) : ViewModel() {
    
    private val _getTeamsState = MutableLiveData<NetworkState>(NetworkState.Idle)
    val getTeamsState: LiveData<NetworkState>
        get() = _getTeamsState
    private val _getTeamsResponse: MutableLiveData<GetTeamsResponse> = MutableLiveData()
    val getTeamsResponse: LiveData<GetTeamsResponse>
        get() = _getTeamsResponse

    init {
        setupGetTeamsStateListener()
    }

    private fun setupGetTeamsStateListener() {
        viewModelScope.launch {
            matchRepository.getTeamsState.collect { state ->
                _getTeamsState.postValue(state)
            }
        }
    }

    fun getTeams(team1Id: Int, team2Id: Int) {
        viewModelScope.launch {
            matchRepository.getTeams(team1Id, team2Id)?.let { result ->
                _getTeamsResponse.value = result
            }
        }
    }
}