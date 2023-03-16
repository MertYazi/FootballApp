package com.mertyazi.footballapp.shared.data.entities

data class ScoreXEntity(
    val duration: String,
    val fullTime: FullTimeEntity,
    val halfTime: HalfTimeEntity,
    val winner: String?
)