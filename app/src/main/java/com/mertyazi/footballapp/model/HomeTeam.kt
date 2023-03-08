package com.mertyazi.footballapp.model

import java.io.Serializable

data class HomeTeam(
    val bench: List<Bench>,
    val coach: Coach,
    val crest: String,
    val formation: String,
    val id: Int,
    val leagueRank: Int,
    val lineup: List<Lineup>,
    val name: String,
    val shortName: String,
    val tla: String
): Serializable