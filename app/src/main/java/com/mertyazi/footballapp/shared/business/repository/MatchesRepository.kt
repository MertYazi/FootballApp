package com.mertyazi.footballapp.shared.business.repository

import com.mertyazi.footballapp.league_table.business.entities.LeagueTable
import com.mertyazi.footballapp.shared.business.entities.Leagues
import com.mertyazi.footballapp.shared.business.entities.Matches
import com.mertyazi.footballapp.shared.data.repository.api.Result

interface MatchesRepository {

    suspend fun getMatchesToday(): Result<Matches>

    suspend fun getLeagues(): Result<Leagues>

    suspend fun getLeagueTable(code: String): Result<LeagueTable>

    suspend fun getTeamDetails(id: Int): Result<Matches>

}