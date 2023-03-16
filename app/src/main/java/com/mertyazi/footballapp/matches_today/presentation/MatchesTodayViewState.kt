package com.mertyazi.footballapp.matches_today.presentation

import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState
import com.mertyazi.footballapp.team_details.presentation.MatchesViewState

sealed class MatchesTodayViewState {
    object Loading : MatchesTodayViewState()
    object Error : MatchesTodayViewState()
    data class ContentMatchesToday(
        val matchesToday: MatchesViewState) : MatchesTodayViewState()
    data class ContentLeagues(
        val leagues: MutableList<CompetitionXViewState>) : MatchesTodayViewState()
}