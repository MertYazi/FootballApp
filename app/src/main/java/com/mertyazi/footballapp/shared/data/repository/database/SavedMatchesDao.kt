package com.mertyazi.footballapp.shared.data.repository.database

import androidx.room.*
import com.mertyazi.footballapp.shared.business.entities.Matche

@Dao
interface SavedMatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match: Matche)

    @Query("SELECT * from matches")
    fun getAllMatches(): List<Matche>

    @Delete
    suspend fun deleteMatch(match: Matche)

}