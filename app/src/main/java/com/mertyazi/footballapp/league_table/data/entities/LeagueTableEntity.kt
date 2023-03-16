package com.mertyazi.footballapp.league_table.data.entities

data class LeagueTableEntity(
    val area: AreaXXEntity,
    val competition: CompetitionXXEntity,
    val filters: FiltersXXEntity,
    val season: SeasonXEntity,
    val standings: List<StandingEntity>
)