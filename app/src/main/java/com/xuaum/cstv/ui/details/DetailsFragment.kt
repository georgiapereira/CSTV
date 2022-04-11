package com.xuaum.cstv.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.repository.match.MatchRepositoryImp
import com.xuaum.cstv.databinding.FragmentDetailsBinding
import com.xuaum.cstv.util.MyDateFormatter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()
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
                    binding.teamsLoadingBar.visibility = View.VISIBLE
                }
                is NetworkState.Success -> {
                    state.value?.let { teams ->
                        Log.i(TAG, "setupGetTeamsStateObserver: $teams")
                        val (team1, team2) = if (teams[0].id == args.team1Id) teams else teams.reversed()


                        binding.detailsTeam1Name.text = team1.name
                        binding.detailsTeam2Name.text = team2.name

                        Glide.with(this).apply {
                            load(team1.image_url)
                                .fitCenter()
                                .placeholder(R.drawable.circle_placeholder)
                                .into(binding.detailsTeam1Logo)

                            load(team2.image_url)
                                .fitCenter()
                                .placeholder(R.drawable.circle_placeholder)
                                .into(binding.detailsTeam2Logo)
                        }

                        binding.team1PlayersContainer.adapter = PlayersAdapter(
                            team1.players,
                            left = true,
                            requireContext()
                        )

                        binding.team2PlayersContainer.adapter = PlayersAdapter(
                            team2.players,
                            left = false,
                            requireContext()
                        )
                        binding.teamsLoading.visibility = View.GONE
                    }

                    Log.i(TAG, "setupGetTeamsStateObserver: Sucesso")
                }
                is NetworkState.Failed -> {
                    binding.teamsLoadingBar.visibility = View.GONE
                    Log.i(TAG, "setupGetTeamsStateObserver: ${state.exception}")
                    Toast.makeText(context, "Erro na requisição", Toast.LENGTH_LONG).show()
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
        binding.detailsMatchTime.text = MyDateFormatter.stringToAppDateString(args.matchTime)
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }
}