package com.mertyazi.footballapp.league_table.business.mapper

import com.mertyazi.footballapp.league_table.business.entities.Table
import com.mertyazi.footballapp.league_table.presentation.TableViewState
import javax.inject.Inject

class TableToTableViewStateMapper @Inject constructor(
): Function1<Table, TableViewState> {
    override fun invoke(table: Table): TableViewState {
        return TableViewState(
            table.draw,
            table.goalDifference,
            table.goalsAgainst,
            table.goalsFor,
            table.lost,
            table.playedGames,
            table.points,
            table.position,
            table.team,
            table.won
        )
    }
}