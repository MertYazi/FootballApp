package com.mertyazi.footballapp.model

import java.io.Serializable

data class Goal(
    val minute: Int,
    val score: Score,
    val scorer: Scorer,
    val team: Team,
    val type: String
): Serializable