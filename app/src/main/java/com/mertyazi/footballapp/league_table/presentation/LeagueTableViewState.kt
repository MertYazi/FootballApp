package com.mertyazi.footballapp.league_table.presentation

sealed class LeagueTableViewState {
    object Loading : LeagueTableViewState()
    object Error : LeagueTableViewState()
    data class ContentLeagueTable(
        val standings: MutableList<StandingViewState>) : LeagueTableViewState()
}