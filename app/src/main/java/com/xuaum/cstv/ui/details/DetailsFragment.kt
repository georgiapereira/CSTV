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
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.NetworkState
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.data.model.response.getteamsresponse.Player
import com.xuaum.cstv.data.repository.MatchRepository
import com.xuaum.cstv.data.service.RetrofitMatchAPI
import com.xuaum.cstv.databinding.FragmentDetailsBinding
import com.xuaum.cstv.ui.home.MatchesAdapter
import com.xuaum.cstv.util.MyDateFormatter


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
                    binding.teamsLoadingBar.visibility = View.VISIBLE
                }
                is NetworkState.Success -> {
                    viewModel.getTeamsResponse.value?.let { teams ->
                        Log.i(TAG, "setupGetTeamsStateObserver: $teams")
                        val (team1, team2) = if (teams[0].id == args.team1Id) teams else teams.reversed()

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


                        val loading = CircularProgressDrawable(requireContext())

                        binding.team1PlayersContainer.adapter = PlayersAdapter(
                            team1.players,
                            left = true,
                            glide,
                            loading
                        )

                        binding.team2PlayersContainer.adapter = PlayersAdapter(
                            team2.players,
                            left = false,
                            glide,
                            loading
                        )
                        binding.teamsLoading.visibility = View.GONE
                    } ?: run {
                        Log.i(TAG, "setupGetTeamsStateObserver: Precisou recarregar")
                        viewModel.getTeams(args.team1Id, args.team2Id)
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
        binding.detailsMatchTime.text = MyDateFormatter().stringToAppDateString(args.matchTime)
    }

    companion object {
        private const val TAG = "DetailsFragment"
    }
}