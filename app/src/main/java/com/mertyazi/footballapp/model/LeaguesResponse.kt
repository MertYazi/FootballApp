package com.mertyazi.footballapp.model

import java.io.Serializable

data class LeaguesResponse(
    val competitions: List<CompetitionX>,
    val count: Int,
    val filters: FiltersX
): Serializable