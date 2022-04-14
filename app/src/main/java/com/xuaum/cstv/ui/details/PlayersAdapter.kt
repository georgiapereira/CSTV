package com.xuaum.cstv.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.Side
import com.xuaum.cstv.data.model.response.getteamsresponse.RawPlayer
import com.xuaum.cstv.databinding.PlayerItemBinding

class PlayersAdapter(
    private val players: List<RawPlayer>,
    private val side: Side,
    private val context: Context
) : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.player_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position])

    }

    override fun getItemCount(): Int = players.size

    override fun getItemViewType(position: Int): Int {
        return side.ordinal
    }

    inner class PlayerViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = PlayerItemBinding.bind(viewItem)

        fun bind(player: RawPlayer) {
            if (itemViewType == Side.Right.ordinal) {
                binding.root.scaleX = (-1).toFloat()
                binding.nicknameText.scaleX = (-1).toFloat()
                binding.nameText.scaleX = (-1).toFloat()
                binding.playerImage.scaleX = (-1).toFloat()
                binding.nicknameText.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                binding.nameText.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            binding.nicknameText.text = player.nickname
            binding.nameText.text =
                "${player.first_name?.trim() ?: ""} ${player.last_name?.trim() ?: ""}"

            val loading = CircularProgressDrawable(context)
            loading.centerRadius = 35f
            loading.strokeWidth = 3f
            loading.start()

            Glide.with(context)
                .load(player.image_url)
                .transform(RoundedCorners(8))
                .placeholder(loading)
                .fallback(R.drawable.rounded_square_placeholder)
                .into(binding.playerImage)

        }
    }
}