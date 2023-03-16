package com.mertyazi.footballapp.saved_matches.business.usecase

import com.mertyazi.footballapp.shared.business.entities.Matche
import com.mertyazi.footballapp.shared.business.repository.SavedMatchesRepository
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState
import javax.inject.Inject

class DeleteFromSavedMatchesUseCase @Inject constructor(
    private val savedMatchesRepository: SavedMatchesRepository
) {

    suspend fun execute(match: MatcheViewState) {
        savedMatchesRepository.removeFromSavedMatches(
            Matche(
                match.awayTeam,
                match.competition,
                match.homeTeam,
                match.id,
                match.minute,
                match.score,
                match.status,
                match.utcDate
            )
        )
    }
}