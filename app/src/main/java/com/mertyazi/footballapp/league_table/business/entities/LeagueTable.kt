package com.mertyazi.footballapp.league_table.business.entities

data class LeagueTable(
    val area: AreaXX,
    val competition: CompetitionXX,
    val standings: List<Standing>
)