package com.mertyazi.footballapp.shared.data.repository.api

import com.mertyazi.footballapp.shared.data.entities.LeaguesEntity
import com.mertyazi.footballapp.shared.data.entities.MatchesEntity
import com.mertyazi.footballapp.league_table.data.entities.LeagueTableEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MatchesService {

    @GET("/v4/matches")
    suspend fun getMatchesToday(
    ): MatchesEntity

    @GET("/v4/competitions")
    suspend fun getLeagues(
    ): LeaguesEntity

    @GET("/v4/competitions/{code}/standings")
    suspend fun getLeagueTable(
        @Path("code") code: String
    ): LeagueTableEntity

    @GET("/v4/teams/{id}/matches")
    suspend fun getTeamDetails(
        @Path("id") id: Int
    ): MatchesEntity
}