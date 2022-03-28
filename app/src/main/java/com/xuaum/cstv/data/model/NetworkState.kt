package com.xuaum.cstv.data.model

sealed class NetworkState {
    object Idle : NetworkState()
    object Loading : NetworkState()
    object Success : NetworkState()
    class Failed(val exception: Exception) : NetworkState()
}