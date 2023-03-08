package com.mertyazi.footballapp.model

import java.io.Serializable

data class ScoreX(
    val duration: String,
    val fullTime: FullTime,
    val halfTime: HalfTime,
    val winner: String
): Serializable