package com.mertyazi.footballapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mertyazi.footballapp.model.*

class Converters {

    @TypeConverter
    fun fromArea(area: Area): String {
        return Gson().toJson(area)
    }

    @TypeConverter
    fun toArea(areaString: String): Area {
        return Gson().fromJson(areaString, object : TypeToken<Area>() {}.type)
    }

    @TypeConverter
    fun fromAwayTeam(awayTeam: AwayTeam): String {
        return Gson().toJson(awayTeam)
    }

    @TypeConverter
    fun toAwayTeam(awayTeamString: String): AwayTeam {
        return Gson().fromJson(awayTeamString, object : TypeToken<AwayTeam>() {}.type)
    }

    @TypeConverter
    fun fromCompetition(competition: Competition): String {
        return Gson().toJson(competition)
    }

    @TypeConverter
    fun toCompetition(competitionString: String): Competition {
        return Gson().fromJson(competitionString, object : TypeToken<Competition>() {}.type)
    }

    @TypeConverter
    fun fromGoal(goalList: List<Goal>?): String {
        return Gson().toJson(goalList)
    }

    @TypeConverter
    fun toGoal(goalString: String?): List<Goal>? {
        return Gson().fromJson(goalString, object : TypeToken<List<Goal>?>() {}.type)
    }

    @TypeConverter
    fun fromScore(score: Score?): String {
        return Gson().toJson(score)
    }

    @TypeConverter
    fun toScore(scoreString: String?): Score {
        return Gson().fromJson(scoreString, object : TypeToken<Score>() {}.type)
    }

    @TypeConverter
    fun fromScorer(scorer: Scorer?): String {
        return Gson().toJson(scorer)
    }

    @TypeConverter
    fun toScorer(scorerString: String?): Scorer {
        return Gson().fromJson(scorerString, object : TypeToken<Scorer>() {}.type)
    }

    @TypeConverter
    fun fromTeam(team: Team): String {
        return Gson().toJson(team)
    }

    @TypeConverter
    fun toTeam(teamString: String): Team {
        return Gson().fromJson(teamString, object : TypeToken<Team>() {}.type)
    }

    @TypeConverter
    fun fromHomeTeam(homeTeam: HomeTeam): String {
        return Gson().toJson(homeTeam)
    }

    @TypeConverter
    fun toHomeTeam(homeTeamString: String): HomeTeam {
        return Gson().fromJson(homeTeamString, object : TypeToken<HomeTeam>() {}.type)
    }

    @TypeConverter
    fun fromOdds(odds: Odds?): String {
        return Gson().toJson(odds)
    }

    @TypeConverter
    fun toOdds(oddsString: String?): Odds {
        return Gson().fromJson(oddsString, object : TypeToken<Odds>() {}.type)
    }

    @TypeConverter
    fun fromScoreX(scoreX: ScoreX): String {
        return Gson().toJson(scoreX)
    }

    @TypeConverter
    fun toScoreX(scoreXString: String): ScoreX {
        return Gson().fromJson(scoreXString, object : TypeToken<ScoreX>() {}.type)
    }

    @TypeConverter
    fun fromSeason(season: Season): String {
        return Gson().toJson(season)
    }

    @TypeConverter
    fun toSeason(seasonString: String): Season {
        return Gson().fromJson(seasonString, object : TypeToken<Season>() {}.type)
    }

    @TypeConverter
    fun fromCompetitionX(competitionX: CompetitionX): String {
        return Gson().toJson(competitionX)
    }

    @TypeConverter
    fun toCompetitionX(competitionXString: String): CompetitionX {
        return Gson().fromJson(competitionXString, object : TypeToken<CompetitionX>() {}.type)
    }

    @TypeConverter
    fun fromAreaX(areaX: AreaX): String {
        return Gson().toJson(areaX)
    }

    @TypeConverter
    fun toAreaX(areaXString: String): AreaX {
        return Gson().fromJson(areaXString, object : TypeToken<AreaX>() {}.type)
    }

    @TypeConverter
    fun fromCurrentSeason(currentSeason: CurrentSeason): String {
        return Gson().toJson(currentSeason)
    }

    @TypeConverter
    fun toCurrentSeason(currentSeasonString: String): CurrentSeason {
        return Gson().fromJson(currentSeasonString, object : TypeToken<CurrentSeason>() {}.type)
    }

    @TypeConverter
    fun fromFilterX(filterX: FiltersX): String {
        return Gson().toJson(filterX)
    }

    @TypeConverter
    fun toFilterX(filterXString: String): FiltersX {
        return Gson().fromJson(filterXString, object : TypeToken<FiltersX>() {}.type)
    }

    @TypeConverter
    fun fromStanding(standing: Standing): String {
        return Gson().toJson(standing)
    }

    @TypeConverter
    fun toStanding(standingString: String): Standing {
        return Gson().fromJson(standingString, object : TypeToken<Standing>() {}.type)
    }

    @TypeConverter
    fun fromTable(table: Table): String {
        return Gson().toJson(table)
    }

    @TypeConverter
    fun toTable(tableString: String): Table {
        return Gson().fromJson(tableString, object : TypeToken<Table>() {}.type)
    }

    @TypeConverter
    fun fromTeamX(teamX: TeamX): String {
        return Gson().toJson(teamX)
    }

    @TypeConverter
    fun toTeamX(teamXString: String): TeamX {
        return Gson().fromJson(teamXString, object : TypeToken<TeamX>() {}.type)
    }

    @TypeConverter
    fun fromSeasonX(seasonX: SeasonX): String {
        return Gson().toJson(seasonX)
    }

    @TypeConverter
    fun toSeasonX(seasonXString: String): SeasonX {
        return Gson().fromJson(seasonXString, object : TypeToken<SeasonX>() {}.type)
    }

    @TypeConverter
    fun fromFiltersXX(filtersXX: FiltersXX): String {
        return Gson().toJson(filtersXX)
    }

    @TypeConverter
    fun toFiltersXX(filtersXXString: String): FiltersXX {
        return Gson().fromJson(filtersXXString, object : TypeToken<FiltersXX>() {}.type)
    }

    @TypeConverter
    fun fromCompetitionXX(competitionXX: CompetitionXX): String {
        return Gson().toJson(competitionXX)
    }

    @TypeConverter
    fun toCompetitionXX(competitionXXString: String): CompetitionXX {
        return Gson().fromJson(competitionXXString, object : TypeToken<CompetitionXX>() {}.type)
    }

    @TypeConverter
    fun fromAreaXX(areaXX: AreaXX): String {
        return Gson().toJson(areaXX)
    }

    @TypeConverter
    fun toAreaXX(areaXXString: String): AreaXX {
        return Gson().fromJson(areaXXString, object : TypeToken<AreaXX>() {}.type)
    }

}