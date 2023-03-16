package com.mertyazi.footballapp.league_table.data.entities

data class SeasonXEntity(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: String
)