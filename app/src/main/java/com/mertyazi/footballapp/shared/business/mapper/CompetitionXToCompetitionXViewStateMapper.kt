package com.mertyazi.footballapp.shared.business.mapper

import com.mertyazi.footballapp.shared.business.entities.CompetitionX
import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState
import javax.inject.Inject

class CompetitionXToCompetitionXViewStateMapper @Inject constructor(
): Function1<CompetitionX, CompetitionXViewState> {
    override fun invoke(competitionX: CompetitionX): CompetitionXViewState {
        return CompetitionXViewState(
            competitionX.area,
            competitionX.code,
            competitionX.emblem,
            competitionX.id,
            competitionX.name
        )
    }
}