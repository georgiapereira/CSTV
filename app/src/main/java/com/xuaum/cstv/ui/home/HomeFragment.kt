package com.xuaum.cstv.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
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
                is NetworkState.Loading-> {
                    binding.matchesLoading.visibility = View.VISIBLE
                }
                is NetworkState.Success -> {
                    viewModel.getMatchesResponse.value?.let { matches ->
                        Log.i(TAG, "setupGetMatchesStateObserver: ${matches.toString()}")
                        binding.matchesContainer.adapter =
                            MatchesAdapter(
                                matches.filter { isValidMatch(it) } as ArrayList<CSMatch>,
                                Glide.with(this),
                                ::onCardClicked
                            )
                    }
                    binding.matchesLoading.visibility = View.GONE
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

    private fun isValidMatch(csMatch: CSMatch): Boolean {
        csMatch.apply {
            return opponents.size == 2 && (status == "running" || status == "not_started")
        }
    }

    private fun onCardClicked(leagueSeries: String, team1Id: Int, team2Id: Int, matchTime: String) {
        this.findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                leagueSeries,
                team1Id,
                team2Id,
                matchTime
            )
        )
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}