package com.xuaum.cstv.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.repository.MatchRepository
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import com.xuaum.cstv.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(MatchRepository(RetrofitMatchAPI))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGetMatchesStateObserver()

        viewModel.getMatches()
    }

    private fun setupGetMatchesStateObserver() {
        viewModel.getMatchesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.Loading -> {

                }
                is NetworkState.Success -> {
                    binding.matchesLoading.visibility = View.GONE
                    viewModel.getMatchesResponse.value?.let { matches ->
                        binding.matchesContainer.adapter = MatchesAdapter(matches, Glide.with(this))
                    }
                    Log.i(TAG, "setupGetMatchesStateObserver: Sucesso")
                }
                is NetworkState.Failed -> {
                    binding.matchesLoading.visibility = View.GONE
                    Log.i(TAG, "setupGetMatchesStateObserver: ${state.exception}")
                }
                else -> {}
            }
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}