package com.mertyazi.footballapp.league_table.business.entities

data class Table(
    val draw: Int,
    val goalDifference: Int,
    val goalsAgainst: Int,
    val goalsFor: Int,
    val lost: Int,
    val playedGames: Int,
    val points: Int,
    val position: Int,
    val team: TeamX,
    val won: Int
)