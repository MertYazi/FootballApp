package com.mertyazi.footballapp.database

import com.mertyazi.footballapp.model.Matche
import com.mertyazi.footballapp.network.FootballAPIService

class MatchesRepository(private val matchesDao: MatchesDao) {

    suspend fun getMatchesToday() = FootballAPIService.api.getMatchesToday()

    suspend fun getLeagues() = FootballAPIService.api.getLeagues()

    suspend fun getMatches(id: Int) = FootballAPIService.api.getMatches(id)

    suspend fun getLeagueTable(code: String) = FootballAPIService.api.getLeagueTable(code)

    suspend fun insert(match: Matche) = matchesDao.insert(match)

    fun getSavedMatches() = matchesDao.getAllMatches()

    suspend fun deleteMatch(match: Matche) = matchesDao.deleteMatch(match)
}