package com.mertyazi.footballapp.model

import java.io.Serializable

data class Table(
    val draw: Int,
    val form: String,
    val goalDifference: Int,
    val goalsAgainst: Int,
    val goalsFor: Int,
    val lost: Int,
    val playedGames: Int,
    val points: Int,
    val position: Int,
    val team: TeamX,
    val won: Int
): Serializable