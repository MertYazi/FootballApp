package com.mertyazi.footballapp.shared.business.entities

data class ScoreX(
    val duration: String,
    val fullTime: FullTime,
    val halfTime: HalfTime,
    val winner: String?
)