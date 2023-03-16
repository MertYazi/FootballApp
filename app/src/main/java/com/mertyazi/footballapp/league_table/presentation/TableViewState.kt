package com.mertyazi.footballapp.league_table.presentation

import com.mertyazi.footballapp.league_table.business.entities.TeamX

data class TableViewState(
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