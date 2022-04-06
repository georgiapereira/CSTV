package com.xuaum.cstv.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.xuaum.cstv.R
import com.xuaum.cstv.data.model.response.getteamsresponse.Player
import com.xuaum.cstv.databinding.PlayerItemBinding

class PlayersAdapter(
    private val players: ArrayList<Player>,
    private val left: Boolean,
    private val glide: RequestManager,
    private val loading: CircularProgressDrawable
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
        return if (left) LEFT else RIGHT
    }

    inner class PlayerViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {
        private val binding = PlayerItemBinding.bind(viewItem)

        fun bind(player: Player) {
            if (itemViewType == RIGHT) {
                binding.root.scaleX = (-1).toFloat()
                binding.nicknameText.scaleX = (-1).toFloat()
                binding.nameText.scaleX = (-1).toFloat()
                binding.playerImage.scaleX = (-1).toFloat()
                binding.nicknameText.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                binding.nameText.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }
            binding.nicknameText.text = player.name
            binding.nameText.text =
                "${player.first_name?.trim() ?: ""} ${player.last_name?.trim() ?: ""}"
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