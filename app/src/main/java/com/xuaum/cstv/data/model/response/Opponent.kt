package com.xuaum.cstv.data.model.response

import com.xuaum.cstv.data.model.response.getmatchesresponse.RawOpponent

data class Opponent(
    val id: Int,
    val image_url: String?,
    val name: String
)

fun RawOpponent.toOpponent() = opponent.run {
    Opponent(id, image_url, name)
}