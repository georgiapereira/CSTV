package com.xuaum.cstv.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getteamsresponse.Team
import com.xuaum.cstv.data.repository.team.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _getTeamsResponse: MutableStateFlow<NetworkState<List<Team>>> =
        MutableStateFlow(NetworkState.Idle)
    val getTeamsState: StateFlow<NetworkState<List<Team>>>
        get() = _getTeamsResponse

    fun getTeams(team1Id: Int, team2Id: Int) {

        viewModelScope.launch {
            teamRepository.getTeams(team1Id, team2Id).collectLatest { state ->
                _getTeamsResponse.value = state
            }
        }
    }
}