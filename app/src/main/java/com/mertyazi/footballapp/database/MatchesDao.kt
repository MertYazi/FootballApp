package com.mertyazi.footballapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mertyazi.footballapp.model.Matche

@Dao
interface MatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Matche): Long

    @Query("SELECT * from matches")
    fun getAllMatches(): LiveData<List<Matche>>

    @Delete
    suspend fun deleteMatch(match: Matche)
}