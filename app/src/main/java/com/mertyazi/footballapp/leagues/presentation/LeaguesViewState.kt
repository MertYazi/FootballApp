package com.mertyazi.footballapp.leagues.presentation

import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState

sealed class LeaguesViewState {
    object Loading : LeaguesViewState()
    object Error : LeaguesViewState()
    data class ContentLeagues(
        val leagues: MutableList<CompetitionXViewState>) : LeaguesViewState()
}