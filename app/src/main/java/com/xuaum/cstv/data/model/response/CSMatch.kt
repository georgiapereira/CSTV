package com.xuaum.cstv.data.model.response

data class CSMatch(
    val begin_at: String,
    val end_at: String,
    val id: Int,
    val league: League,
    val league_id: Int,
    val modified_at: String,
    val name: String,
    val number_of_games: Int,
    val opponents: List<Opponent>,
    val original_scheduled_at: String,
    val rescheduled: Boolean,
    val results: List<Result>,
    val scheduled_at: String,
    val serie: Serie,
    val serie_id: Int,
    val slug: String,
    val status: String,
    val tournament: Tournament,
    val tournament_id: Int,
    val videogame: Videogame,
    val videogame_version: Any,
    val winner: WinnerX,
    val winner_id: Int
)