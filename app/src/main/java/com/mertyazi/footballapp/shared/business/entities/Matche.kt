package com.mertyazi.footballapp.shared.business.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "matches")
data class Matche(
    @ColumnInfo(name = "away_team") val awayTeam: AwayTeam,
    @ColumnInfo(name = "competition") val competition: Competition,
    @ColumnInfo(name = "home_team") val homeTeam: HomeTeam,
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "minute") val minute: String?,
    @ColumnInfo(name = "score") val score: ScoreX,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "utc_date") var utcDate: String?
): Serializable