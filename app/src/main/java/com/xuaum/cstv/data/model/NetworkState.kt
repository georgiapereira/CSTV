package com.xuaum.cstv.data.model

sealed class NetworkState<out T: Any?> {
    object Idle : NetworkState<Nothing>()
    object Loading : NetworkState<Nothing>()
    data class Success<out T>(val value: T? = null) : NetworkState<T>()
    data class Failed(val exception: Exception) : NetworkState<Nothing>()
}