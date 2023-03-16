package com.mertyazi.footballapp.shared.data.entities

data class CompetitionXEntity(
    val area: AreaXEntity?,
    val code: String,
    val currentSeason: CurrentSeasonEntity?,
    var emblem: String,
    val id: Int,
    val lastUpdated: String,
    var name: String,
    val numberOfAvailableSeasons: Int,
    val plan: String,
    val type: String
)