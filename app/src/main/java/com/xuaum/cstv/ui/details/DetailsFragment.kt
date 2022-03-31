package com.xuaum.cstv.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Player
import com.xuaum.cstv.data.repository.MatchRepository
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import com.xuaum.cstv.databinding.FragmentDetailsBinding
import com.xuaum.cstv.ui.home.MatchesAdapter


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(MatchRepository(RetrofitMatchAPI))
    }
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMatchInfo()
        setupBackButtonListener()
        setupGetTeamsStateObserver()

        viewModel.getTeams(args.team1Id, args.team2Id)
    }

    private fun setupGetTeamsStateObserver() {
        viewModel.getTeamsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.Loading -> {
                    binding.teamsLoading.visibility = View.VISIBLE
                }
                is NetworkState.Success -> {
                    viewModel.getTeamsResponse.value?.let { teams ->
                        Log.i(TAG, "setupGetTeamsStateObserver: $teams")
                        val team1 = teams[0]
                        val team2 = teams[1]

                        val glide = Glide.with(this)

                        binding.detailsTeam1Name.text = team1.name
                        glide.load(team1.image_url)
                            .fitCenter()
                            .placeholder(R.drawable.circle_placeholder)
                            .into(binding.detailsTeam1Logo)

                        binding.detailsTeam2Name.text = team2.name
                        glide.load(team2.image_url)
                            .fitCenter()
                            .placeholder(R.drawable.circle_placeholder)
                            .into(binding.detailsTeam2Logo)

                        binding.team1PlayersContainer.adapter = PlayersAdapter(
                            team1.players as ArrayList<Player>,
                            left = true,
                            glide
                        )
                    }
                    binding.teamsLoading.visibility = View.GONE
                    Log.i(TAG, "setupGetTeamsStateObserver: Sucesso")
                }
                is NetworkState.Failed -> {
                    binding.teamsLoading.visibility = View.GONE
                    Log.i(TAG, "setupGetTeamsStateObserver: ${state.exception}")
                }
                else -> {}
            }
        }
    }

    private fun setupBackButtonListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupMatchInfo() {
        binding.detailsLeagueName.text = args.leagueSerie
        binding.detailsMatchTime.text = args.matchTime
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }
}