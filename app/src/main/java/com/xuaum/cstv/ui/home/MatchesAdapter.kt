package com.xuaum.cstv.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.databinding.MatchCardBinding

class MatchesAdapter(
    private val csMatches: ArrayList<CSMatch>,
    private val glide: RequestManager,
    private val onCardClicked: (leagueSeries: String, team1Id: Int, team2Id: Int, matchTime: String) -> Unit
) : RecyclerView.Adapter<MatchesAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.match_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(csMatches[position])
    }

    override fun getItemCount(): Int = csMatches.size


    inner class MatchViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = MatchCardBinding.bind(viewItem)

        fun bind(match: CSMatch) {
            val time = match.begin_at
            val team1 = match.opponents[0].opponent
            val team2 = match.opponents[1].opponent
            val league = "${match.league.name} ${match.serie.full_name}"

            binding.cardContainer.setOnClickListener {
                onCardClicked(league, team1.id, team2.id, time)
            }

            binding.matchTime.text = match.begin_at

            binding.leagueName.text = league
            glide
                .load(match.league.image_url)
                .fitCenter()
                .placeholder(R.drawable.circle_placeholder)
                .into(binding.leagueLogo)

            binding.matchTime.isSelected = match.status == "running"

            binding.team1Name.text = team1.name
            glide
                .load(team1.image_url)
                .fitCenter()
                .placeholder(R.drawable.circle_placeholder)
                .into(binding.team1Logo)

            binding.team2Name.text = team2.name
            glide
                .load(team2.image_url)
                .fitCenter()
                .placeholder(R.drawable.circle_placeholder)
                .into(binding.team2Logo)
        }
    }
}