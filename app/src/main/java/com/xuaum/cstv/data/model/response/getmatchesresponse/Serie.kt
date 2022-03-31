package com.xuaum.cstv.data.model.response.getmatchesresponse

data class Serie(
    val begin_at: String,
    val description: Any,
    val end_at: String,
    val full_name: String,
    val id: Int,
    val league_id: Int,
    val modified_at: String,
    val name: Any,
    val season: String,
    val slug: String,
    val tier: String,
    val winner_id: Any,
    val winner_type: Any,
    val year: Int
)