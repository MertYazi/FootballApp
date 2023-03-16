package com.mertyazi.footballapp.shared.data.repository.api

import android.util.Log
import com.mertyazi.footballapp.league_table.business.entities.LeagueTable
import com.mertyazi.footballapp.league_table.business.mapper.LeagueTableEntityToLeagueTableMapper
import com.mertyazi.footballapp.shared.business.entities.Leagues
import com.mertyazi.footballapp.shared.business.mapper.CompetitionXEntityToCompetitionXMapper
import com.mertyazi.footballapp.shared.business.entities.Matches
import com.mertyazi.footballapp.shared.business.mapper.MatcheEntityToMatcheMapper
import com.mertyazi.footballapp.shared.business.repository.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchesRepositoryAPI @Inject constructor(
    private val service: MatchesService,
    private val matcheEntityToMatcheMapper: MatcheEntityToMatcheMapper,
    private val competitionXEntityToCompetitionXMapper: CompetitionXEntityToCompetitionXMapper,
    private val leagueTableEntityToLeagueTableMapper: LeagueTableEntityToLeagueTableMapper
): MatchesRepository {
    override suspend fun getMatchesToday(): Result<Matches> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getMatchesToday()
                val data = Matches(
                    response.matches.map {
                        matcheEntityToMatcheMapper.invoke(it)
                    }.toMutableList()
                )
                if (data.matches.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty matches today list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayer", exception.message, exception)
                    Result.Error(exception)
            }
        }
    }

    override suspend fun getLeagues(): Result<Leagues> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getLeagues()
                val data = Leagues(
                    response.competitions.map {
                        competitionXEntityToCompetitionXMapper.invoke(it)
                    }.toMutableList()
                )
                if (data.competitions.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty leagues list"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayer", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getLeagueTable(code: String): Result<LeagueTable> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getLeagueTable(code)
                val data = leagueTableEntityToLeagueTableMapper.invoke(response)
                if (data.standings.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty league table"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayer", exception.message, exception)
                Result.Error(exception)
            }
        }
    }

    override suspend fun getTeamDetails(id: Int): Result<Matches> {
        return withContext(Dispatchers.IO) {
            try {
                val response = service.getTeamDetails(id)
                val data = Matches(
                    response.matches.map {
                        matcheEntityToMatcheMapper.invoke(it)
                    }.toMutableList()
                )
                if (data.matches.isNotEmpty()) {
                    Result.Success(data)
                } else {
                    Result.Error(IllegalStateException("Empty team details matches"))
                }
            } catch (exception: Exception) {
                Log.e("NetworkLayer", exception.message, exception)
                Result.Error(exception)
            }
        }
    }
}