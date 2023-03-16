package com.mertyazi.footballapp.shared.business.mapper

import com.mertyazi.footballapp.shared.business.entities.Matche
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState
import javax.inject.Inject

class MatcheToMatcheViewStateMapper @Inject constructor(
): Function1<Matche, MatcheViewState> {

    override fun invoke(matche: Matche): MatcheViewState {
        return MatcheViewState(
            matche.awayTeam,
            matche.competition,
            matche.homeTeam,
            matche.id,
            matche.minute,
            matche.score,
            matche.status,
            matche.utcDate
        )
    }
}