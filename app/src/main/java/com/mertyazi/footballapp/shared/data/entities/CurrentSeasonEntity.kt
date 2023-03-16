package com.mertyazi.footballapp.shared.data.entities

data class CurrentSeasonEntity(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: String
)