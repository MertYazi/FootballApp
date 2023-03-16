package com.mertyazi.footballapp.shared.data.entities

data class SeasonEntity(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val stages: List<String>,
    val startDate: String,
    val winner: Any
)