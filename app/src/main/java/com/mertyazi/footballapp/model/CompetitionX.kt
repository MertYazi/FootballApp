package com.mertyazi.footballapp.model

import java.io.Serializable

data class CompetitionX(
    val area: AreaX?,
    val code: String,
    val currentSeason: CurrentSeason?,
    var emblem: String,
    val id: Int,
    val lastUpdated: String,
    var name: String,
    val numberOfAvailableSeasons: Int,
    val plan: String,
    val type: String
):Serializable {
    constructor(): this(null, "", null, "", 0, "", "", 0, "", "")
}