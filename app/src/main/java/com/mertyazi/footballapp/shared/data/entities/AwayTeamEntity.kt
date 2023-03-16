package com.mertyazi.footballapp.shared.data.entities

data class AwayTeamEntity(
    val bench: List<BenchEntity>,
    val coach: CoachEntity,
    val crest: String,
    val formation: String,
    val id: Int,
    val leagueRank: Int,
    val lineup: List<LineupEntity>,
    val name: String,
    val shortName: String,
    val tla: String
)