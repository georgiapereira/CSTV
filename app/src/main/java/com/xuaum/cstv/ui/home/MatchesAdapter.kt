package com.xuaum.cstv.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.response.CSMatch
import com.xuaum.cstv.databinding.MatchCardBinding
import com.xuaum.cstv.util.MyDateFormatter

class MatchesAdapter(
    private val context: Context,
    private val onCardClicked: (leagueSeries: String, team1Id: Int, team2Id: Int, matchTime: String) -> Unit
) : PagingDataAdapter<CSMatch, MatchesAdapter.MatchViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.match_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MatchViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = MatchCardBinding.bind(viewItem)

        fun bind(match: CSMatch?) {
            match?.let {
                val time = match.beginAt
                val (team1, team2) = match.opponents
                val league = "${match.league.name} ${match.seriesName}"

                binding.cardContainer.setOnClickListener {
                    onCardClicked(league, team1.id, team2.id, time)
                }

                binding.leagueName.text = league
                Glide.with(context)
                    .load(match.league.image_url)
                    .fitCenter()
                    .placeholder(R.drawable.circle_placeholder)
                    .fallback(R.drawable.circle_placeholder)
                    .into(binding.leagueLogo)

                if (match.status == "running") {
                    binding.matchTime.isSelected = true
                    binding.matchTime.text = "AGORA"
                } else {
                    binding.matchTime.isSelected = false
                    val dateFormatted = MyDateFormatter().stringToAppDateString(match.beginAt)
                    binding.matchTime.text = dateFormatted
                }

//                val loading = CircularProgressDrawable(context)
//                loading.centerRadius = 35f
//                loading.strokeWidth = 3f
//                loading.start()

                binding.team1Name.text = team1.name
                binding.team2Name.text = team2.name
                Glide.with(context).apply {
                    load(team1.image_url)
                        .fitCenter()
                        .placeholder(R.drawable.circle_placeholder)
                        .fallback(R.drawable.circle_placeholder)
                        .into(binding.team1Logo)


                    load(team2.image_url)
                        .fitCenter()
                        .placeholder(R.drawable.circle_placeholder)
                        .fallback(R.drawable.circle_placeholder)
                        .into(binding.team2Logo)
                }

            } ?: run {
                //Default pedido pela api de paging
                binding.matchTime.text = ""
                binding.leagueName.text = ""
                binding.leagueLogo.setBackgroundResource(R.drawable.circle_placeholder)
                binding.team1Name.text = ""
                binding.team1Logo.setBackgroundResource(R.drawable.circle_placeholder)
                binding.team2Name.text = ""
                binding.team2Logo.setBackgroundResource(R.drawable.circle_placeholder)
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<CSMatch>() {
            override fun areItemsTheSame(oldItem: CSMatch, newItem: CSMatch): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CSMatch, newItem: CSMatch): Boolean {
                return oldItem == newItem
            }
        }
    }
}