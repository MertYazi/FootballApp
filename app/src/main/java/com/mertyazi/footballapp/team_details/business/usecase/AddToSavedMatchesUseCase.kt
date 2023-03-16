package com.mertyazi.footballapp.team_details.business.usecase

import com.mertyazi.footballapp.shared.business.entities.Matche
import com.mertyazi.footballapp.shared.business.repository.SavedMatchesRepository
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState
import javax.inject.Inject

class AddToSavedMatchesUseCase @Inject constructor(
    private val savedMatchesRepository: SavedMatchesRepository
) {

    suspend fun execute(match: MatcheViewState) {
        savedMatchesRepository.addToSavedMatches(
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