package com.xuaum.cstv.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xuaum.cstv.data.repository.MatchRepository

class HomeViewModelFactory(private val matchRepository: MatchRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == HomeViewModel::class.java) {
            HomeViewModel(matchRepository) as T
        } else {
            super.create(modelClass)
        }

    }
}