package com.xuaum.cstv.data.model.response.getteamsresponse

data class GetTeamsResponseItem(
    val acronym: Any,
    val current_videogame: CurrentVideogame,
    val id: Int,
    val image_url: String,
    val location: String,
    val modified_at: String,
    val name: String,
    val players: List<Player>,
    val slug: String
)