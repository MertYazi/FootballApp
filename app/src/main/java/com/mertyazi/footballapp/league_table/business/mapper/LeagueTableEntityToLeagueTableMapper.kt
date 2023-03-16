package com.mertyazi.footballapp.league_table.business.mapper

import com.mertyazi.footballapp.league_table.business.entities.*
import com.mertyazi.footballapp.league_table.data.entities.LeagueTableEntity
import javax.inject.Inject

class LeagueTableEntityToLeagueTableMapper @Inject constructor(
): Function1<LeagueTableEntity, LeagueTable> {
    override fun invoke(leagueTableEntity: LeagueTableEntity): LeagueTable {
        return LeagueTable(
            AreaXX(
                leagueTableEntity.area.code,
                leagueTableEntity.area.flag,
                leagueTableEntity.area.id,
                leagueTableEntity.area.name,
            ),
            CompetitionXX(
                leagueTableEntity.competition.code,
                leagueTableEntity.competition.emblem,
                leagueTableEntity.competition.id,
                leagueTableEntity.competition.name,
                leagueTableEntity.competition.type,
            ),
            leagueTableEntity.standings.map {
                Standing(
                    it.stage,
                    it.table.map { tableEntity ->
                        Table(
                            tableEntity.draw,
                            tableEntity.goalDifference,
                            tableEntity.goalsAgainst,
                            tableEntity.goalsFor,
                            tableEntity.lost,
                            tableEntity.playedGames,
                            tableEntity.points,
                            tableEntity.position,
                            TeamX(
                                tableEntity.team.crest,
                                tableEntity.team.id,
                                tableEntity.team.name,
                                tableEntity.team.shortName,
                                tableEntity.team.tla,
                            ),
                            tableEntity.won
                        )
                    }
                )
            }
        )
    }
}