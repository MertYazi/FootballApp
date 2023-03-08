package com.mertyazi.footballapp.model

import java.io.Serializable

data class StandingsResponse(
    val area: AreaXX,
    val competition: CompetitionXX,
    val filters: FiltersXX,
    val season: SeasonX,
    val standings: List<Standing>
): Serializable