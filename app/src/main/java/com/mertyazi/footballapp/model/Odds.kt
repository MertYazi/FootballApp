package com.mertyazi.footballapp.model

import java.io.Serializable

data class Odds(
    val awayWin: Double,
    val draw: Double,
    val homeWin: Double
): Serializable