package com.mertyazi.footballapp.shared.data.entities

data class LeaguesEntity(
    val competitions: List<CompetitionXEntity>,
    val count: Int,
    val filters: FiltersXEntity
)