package com.mertyazi.footballapp.shared.business.repository.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mertyazi.footballapp.shared.business.entities.*

class Converters {

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
    fun fromHomeTeam(homeTeam: HomeTeam): String {
        return Gson().toJson(homeTeam)
    }

    @TypeConverter
    fun toHomeTeam(homeTeamString: String): HomeTeam {
        return Gson().fromJson(homeTeamString, object : TypeToken<HomeTeam>() {}.type)
    }

    @TypeConverter
    fun fromScoreX(scoreX: ScoreX): String {
        return Gson().toJson(scoreX)
    }

    @TypeConverter
    fun toScoreX(scoreXString: String): ScoreX {
        return Gson().fromJson(scoreXString, object : TypeToken<ScoreX>() {}.type)
    }
}