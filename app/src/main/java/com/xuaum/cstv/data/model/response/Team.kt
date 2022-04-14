package com.xuaum.cstv.data.model.response

import com.xuaum.cstv.data.model.response.getteamsresponse.RawPlayer
import com.xuaum.cstv.data.model.response.getteamsresponse.RawTeam

data class Team(
    val id: Int,
    val imageUrl: String?,
    val name: String,
    val players: List<Player>
)

fun RawTeam.toTeam(): Team = Team(id, image_url, name, players.map { it.toPlayer() })

