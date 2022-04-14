package com.xuaum.cstv.data.model

import com.xuaum.cstv.data.model.response.getteamsresponse.RawPlayer

data class Player(
    val first_name: String?,
    val id: Int,
    val image_url: Any,
    val last_name: String?,
    val nickname: String,
)

fun RawPlayer.toPlayer(): Player = Player(first_name, id, image_url, last_name, nickname)
