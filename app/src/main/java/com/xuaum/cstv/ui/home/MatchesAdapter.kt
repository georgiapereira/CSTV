package com.xuaum.cstv.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.response.CSMatch
import com.xuaum.cstv.databinding.MatchCardBinding
import kotlin.coroutines.coroutineContext

class MatchesAdapter(
    private val csMatches: ArrayList<CSMatch>,
    private val glideManager: RequestManager
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
            binding.matchTime.text = match.begin_at

            binding.leagueName.text = "${match.league.name} ${match.serie.full_name}"
            glideManager
                .load(match.league.image_url)
                .circleCrop()
                .placeholder(R.drawable.circle_placeholder)
                .into(binding.leagueLogo)


            binding.team1Name.text = match.opponents[0].opponent.name
            binding.team2Name.text = match.opponents[1].opponent.name

            glideManager
                .load(match.opponents[0].opponent.image_url)
                .circleCrop()
                .placeholder(R.drawable.circle_placeholder)
                .into(binding.team1Logo)
            glideManager
                .load(match.opponents[1].opponent.image_url)
                .circleCrop()
                .placeholder(R.drawable.circle_placeholder)
                .into(binding.team2Logo)
        }
    }
}