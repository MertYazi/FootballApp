package com.mertyazi.footballapp.shared.business.mapper

import com.mertyazi.footballapp.shared.business.entities.*
import com.mertyazi.footballapp.shared.data.entities.MatcheEntity
import javax.inject.Inject

class MatcheEntityToMatcheMapper @Inject constructor(
): Function1<MatcheEntity, Matche> {

    override fun invoke(matcheEntity: MatcheEntity): Matche {
        return Matche(
            AwayTeam(
                matcheEntity.awayTeam.crest,
                matcheEntity.awayTeam.id,
                matcheEntity.awayTeam.name,
                matcheEntity.awayTeam.shortName,
                matcheEntity.awayTeam.tla
            ),
            Competition(
                matcheEntity.competition.code,
                matcheEntity.competition.emblem,
                matcheEntity.competition.id,
                matcheEntity.competition.name,
                matcheEntity.competition.type
            ),
            HomeTeam(
                matcheEntity.homeTeam.crest,
                matcheEntity.homeTeam.id,
                matcheEntity.homeTeam.name,
                matcheEntity.homeTeam.shortName,
                matcheEntity.homeTeam.tla
            ),
            matcheEntity.id,
            matcheEntity.minute,
            ScoreX(
                matcheEntity.score.duration,
                FullTime(
                    matcheEntity.score.fullTime.away,
                    matcheEntity.score.fullTime.home
                ),
                HalfTime(
                    matcheEntity.score.halfTime.away,
                    matcheEntity.score.halfTime.home
                ),
                matcheEntity.score.winner
            ),
            matcheEntity.status,
            matcheEntity.utcDate
        )
    }
}