package com.xuaum.cstv.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

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
        setupGetMatchesPagerObserver()
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

    private fun setupGetMatchesPagerObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMatchesPager.collect {
                matchesAdapter.submitData(it)
            }
        }
    }

    private fun setupGetMatchesStateObserver() {
        matchesAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh !is LoadState.Loading) {
                binding.matchesLoading.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false

                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Log.i(TAG, "setupGetMatchesStateObserver: Erro:${errorState.error.message}")
                }?: kotlin.run {
                    Log.i(TAG, "setupGetMatchesStateObserver: Sucesso")
                }
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
