package com.mertyazi.footballapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "matches")
data class Matche(
    val area: Area,
    val awayTeam: AwayTeam,
    val competition: Competition,
    val goals: List<Goal>?,
    val group: String?,
    val homeTeam: HomeTeam,
    @PrimaryKey
    val id: Int,
    val lastUpdated: String?,
    val matchday: Int?,
    val minute: String?,
    val odds: Odds?,
    val score: ScoreX,
    val season: Season,
    val stage: String?,
    val status: String?,
    var utcDate: String?,
    val venue: String?
): Serializable