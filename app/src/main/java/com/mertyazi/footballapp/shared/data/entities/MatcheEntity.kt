package com.mertyazi.footballapp.shared.data.entities

data class MatcheEntity(
    val area: AreaEntity,
    val awayTeam: AwayTeamEntity,
    val competition: CompetitionEntity,
    val goals: List<GoalEntity>?,
    val group: String?,
    val homeTeam: HomeTeamEntity,
    val id: Int,
    val lastUpdated: String?,
    val matchday: Int?,
    val minute: String?,
    val odds: OddsEntity?,
    val score: ScoreXEntity,
    val season: SeasonEntity,
    val stage: String?,
    val status: String?,
    var utcDate: String?,
    val venue: String?
)