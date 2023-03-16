package com.mertyazi.footballapp.shared.business.mapper

import com.mertyazi.footballapp.shared.business.entities.AreaX
import com.mertyazi.footballapp.shared.business.entities.CompetitionX
import com.mertyazi.footballapp.shared.data.entities.CompetitionXEntity
import javax.inject.Inject

class CompetitionXEntityToCompetitionXMapper @Inject constructor(
): Function1<CompetitionXEntity, CompetitionX> {
    override fun invoke(competitionXEntity: CompetitionXEntity): CompetitionX {
        return CompetitionX(
            AreaX(
                competitionXEntity.area?.code ?: "",
                competitionXEntity.area?.flag ?: "",
                competitionXEntity.area?.id ?: 0,
                competitionXEntity.area?.name ?: "",
            ),
            competitionXEntity.code,
            competitionXEntity.emblem,
            competitionXEntity.id,
            competitionXEntity.name
        )
    }
}