package com.xuaum.cstv.data.model.response

import com.xuaum.cstv.data.model.response.getmatchesresponse.RawLeague

data class League(
    val id: Int,
    val image_url: String?,
    val name: String
)

fun RawLeague.toLeague() = League(id, image_url, name)
