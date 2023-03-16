package com.mertyazi.footballapp.team_details.presentation

import com.mertyazi.footballapp.shared.business.entities.*

data class MatcheViewState(
    val awayTeam: AwayTeam,
    val competition: Competition,
    val homeTeam: HomeTeam,
    val id: Int,
    val minute: String?,
    val score: ScoreX,
    val status: String?,
    var utcDate: String?
)
