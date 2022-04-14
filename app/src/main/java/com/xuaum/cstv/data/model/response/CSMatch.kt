package com.xuaum.cstv.data.model.response

import com.xuaum.cstv.data.model.response.getmatchesresponse.RawOpponent
import com.xuaum.cstv.data.model.response.getmatchesresponse.RawCSMatch

data class CSMatch(
    val beginAt: String,
    val id: Int,
    val league: League,
    val opponents: List<Opponent>,
    val seriesName: String,
    val seriesId: Int,
    val status: String
)

fun RawCSMatch.toCSMatch() =
    CSMatch(
        begin_at,
        id,
        league.toLeague(),
        opponents.map { it.toOpponent() },
        serie.full_name,
        serie_id,
        status
    )
