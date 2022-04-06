package com.xuaum.cstv.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.response.getmatchesresponse.CSMatch
import com.xuaum.cstv.databinding.MatchCardBinding
import com.xuaum.cstv.util.MyDateFormatter
import okhttp3.internal.notifyAll
import java.util.*
import kotlin.collections.ArrayList

class MatchesAdapter(
    private val context: Context,
    private val onCardClicked: (leagueSeries: String, team1Id: Int, team2Id: Int, matchTime: String) -> Unit
) : PagingDataAdapter<CSMatch, MatchesAdapter.MatchViewHolder>(diffCallback) {

    private val csMatches: ArrayList<CSMatch> = ArrayList()

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
                val time = match.begin_at
                val team1 = match.opponents[0].opponent
                val team2 = match.opponents[1].opponent
                val league = "${match.league.name} ${match.serie.full_name}"

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
                    val dateFormatted = MyDateFormatter().stringToAppDateString(match.begin_at)
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