package com.xuaum.cstv.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.response.getteamsresponse.Player
import com.xuaum.cstv.databinding.PlayerItemLeftBinding
import com.xuaum.cstv.databinding.PlayerItemRightBinding

class PlayersAdapter(
    private val players: ArrayList<Player>,
    private val left: Boolean,
    private val glide: RequestManager,
    private val loading: CircularProgressDrawable
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LEFT) {
            PlayerLeftViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.player_item_left, parent, false)
            )
        } else {
            PlayerRightViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.player_item_right, parent, false)
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlayerLeftViewHolder -> {
                holder.bind(players[position])
            }
            is PlayerRightViewHolder -> {
                holder.bind(players[position])
            }
        }
    }

    override fun getItemCount(): Int = players.size

    override fun getItemViewType(position: Int): Int {
        return if (left) LEFT else RIGHT
    }

    inner class PlayerLeftViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = PlayerItemLeftBinding.bind(viewItem)

        fun bind(player: Player) {
            binding.nicknameText.text = player.name
            binding.nameText.text =
                "${player.first_name.trim() ?: ""} ${player.last_name.trim() ?: ""}"
            glide.load(player.image_url)
                .transform(RoundedCorners(8))
                .placeholder(R.drawable.rounded_square_placeholder)
                .fallback(R.drawable.rounded_square_placeholder)
                .into(binding.playerImage)

        }
    }

    inner class PlayerRightViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = PlayerItemRightBinding.bind(viewItem)

        fun bind(player: Player) {
            binding.nicknameText.text = player.name
            binding.nameText.text =
                "${player.first_name.trim() ?: ""} ${player.last_name.trim() ?: ""}"
            glide.load(player.image_url)
                .transform(RoundedCorners(8))
                .placeholder(R.drawable.rounded_square_placeholder)
                .fallback(R.drawable.rounded_square_placeholder)
                .into(binding.playerImage)

        }
    }

    companion object {
        private const val LEFT = 1
        private const val RIGHT = 2
    }
}