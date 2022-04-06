package com.xuaum.cstv.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.repository.MatchRepository
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import com.xuaum.cstv.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(MatchRepository(RetrofitMatchAPI))
    }

    private lateinit var matchesAdapter: MatchesAdapter

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

        setupMatchesAdapter()
        setupSwipeRefreshListener()
        setupGetMatchesStateObserver()
        setupGetMatchesPagingResponseObserver()
    }

    private fun setupSwipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            matchesAdapter.refresh()
        }
    }

    private fun setupMatchesAdapter() {
        matchesAdapter =
            MatchesAdapter(
                requireContext(),
                ::onCardClicked
            )
        binding.matchesContainer.adapter = matchesAdapter
    }

    private fun setupGetMatchesPagingResponseObserver() {

        viewModel.getMatchesPagingResponse.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                if(matchesAdapter.itemCount != 0){
                    binding.matchesLoading.visibility = View.GONE
                }
                matchesAdapter.submitData(it)
            }
        }
    }

    private fun setupGetMatchesStateObserver() {
        viewModel.getMatchesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.Success -> {
                    binding.matchesLoading.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    Log.i(TAG, "setupGetMatchesStateObserver: Sucesso")
                }
                is NetworkState.Failed -> {
                    binding.matchesLoading.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    Log.i(TAG, "setupGetMatchesStateObserver: ${state.exception}")
                }
                else -> {}
            }
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
