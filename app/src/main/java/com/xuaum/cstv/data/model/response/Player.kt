package com.xuaum.cstv.data.model.response

import com.xuaum.cstv.data.model.response.getteamsresponse.RawPlayer

data class Player(
    val firstName: String?,
    val id: Int,
    val imageUrl: String?,
    val lastName: String?,
    val nickname: String,
)

fun RawPlayer.toPlayer(): Player = Player(first_name, id, image_url, last_name, nickname)
