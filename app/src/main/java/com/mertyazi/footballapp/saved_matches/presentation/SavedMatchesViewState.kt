package com.mertyazi.footballapp.saved_matches.presentation

import com.mertyazi.footballapp.team_details.presentation.MatcheViewState

sealed class SavedMatchesViewState {
    object Loading : SavedMatchesViewState()
    object Error : SavedMatchesViewState()
    data class ContentSavedMatches(
        val matches: MutableList<MatcheViewState>) : SavedMatchesViewState()
}

fun SavedMatchesViewState.ContentSavedMatches.deleteMatch(
    match: MatcheViewState
): SavedMatchesViewState.ContentSavedMatches {
    return SavedMatchesViewState.ContentSavedMatches(matches =
        if (matches.contains(match)) {
            val allMatches = matches
            allMatches.remove(match)
            allMatches
        } else {
            matches
        }
    )
}