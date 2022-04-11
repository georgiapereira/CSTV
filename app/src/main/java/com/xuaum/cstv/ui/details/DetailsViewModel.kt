package com.xuaum.cstv.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.data.repository.match.MatchRepository
import com.xuaum.cstv.data.repository.match.MatchRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _getTeamsResponse = MutableLiveData<NetworkState<List<Team>>>(NetworkState.Idle)
    val getTeamsState: LiveData<NetworkState<List<Team>>>
        get() = _getTeamsResponse

    init {
        setupGetTeamsStateListener()
    }

    private fun setupGetTeamsStateListener() {
        viewModelScope.launch {
            matchRepository.getTeamsState.collect { state ->
                _getTeamsResponse.postValue(state)
            }
        }
    }

    fun getTeams(team1Id: Int, team2Id: Int) {
        viewModelScope.launch {
            matchRepository.getTeams(team1Id, team2Id)
        }
    }
}