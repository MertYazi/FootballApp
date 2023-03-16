package com.mertyazi.footballapp.league_table.presentation

data class StandingViewState(
    val stage: String,
    val table: List<TableViewState>
)