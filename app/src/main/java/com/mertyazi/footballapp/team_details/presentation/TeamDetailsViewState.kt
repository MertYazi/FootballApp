package com.mertyazi.footballapp.team_details.presentation

sealed class TeamDetailsViewState {
    object Loading : TeamDetailsViewState()
    object Error : TeamDetailsViewState()
    data class ContentTeamDetails(
        val matches: MutableList<MatcheViewState>) : TeamDetailsViewState()
}

fun TeamDetailsViewState.ContentTeamDetails.saveMatch(
    match: MatcheViewState
): TeamDetailsViewState.ContentTeamDetails {
    return TeamDetailsViewState.ContentTeamDetails(matches =
        if (!matches.contains(match)) {
            val allMatches = matches
            allMatches.add(match)
            allMatches
        } else {
            matches
        }
    )
}