package com.mertyazi.footballapp.network

import com.mertyazi.footballapp.BuildConfig
import com.mertyazi.footballapp.model.LeaguesResponse
import com.mertyazi.footballapp.model.MatchesResponse
import com.mertyazi.footballapp.model.StandingsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FootballAPI {

    @GET("/v4/matches")
    suspend fun getMatchesToday(
        @Header("X-Auth-Token") token: String = BuildConfig.API_KEY
    ): Response<MatchesResponse>

    @GET("/v4/competitions")
    suspend fun getLeagues(
        @Header("X-Auth-Token") token: String = BuildConfig.API_KEY
    ): Response<LeaguesResponse>

    @GET("/v4/competitions/{code}/standings")
    suspend fun getLeagueTable(
        @Path("code") code: String,
        @Header("X-Auth-Token") token: String = BuildConfig.API_KEY
    ): Response<StandingsResponse>

    @GET("/v4/teams/{id}/matches")
    suspend fun getMatches(
        @Path("id") code: Int,
        @Header("X-Auth-Token") token: String = BuildConfig.API_KEY
    ): Response<MatchesResponse>
}