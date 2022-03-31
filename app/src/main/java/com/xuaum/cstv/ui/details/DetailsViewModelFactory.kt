package com.xuaum.cstv.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xuaum.cstv.data.repository.MatchRepository

class DetailsViewModelFactory(private val matchRepository: MatchRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == DetailsViewModel::class.java) {
            DetailsViewModel(matchRepository) as T
        } else {
            super.create(modelClass)
        }

    }
}