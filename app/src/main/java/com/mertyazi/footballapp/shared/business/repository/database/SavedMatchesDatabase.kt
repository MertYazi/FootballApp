package com.mertyazi.footballapp.shared.business.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mertyazi.footballapp.shared.business.entities.Matche
import com.mertyazi.footballapp.shared.data.repository.database.SavedMatchesDao

@Database(entities = [Matche::class], version = 1)
@TypeConverters(Converters::class)
abstract class SavedMatchesDatabase : RoomDatabase() {
    abstract fun matchesDao(): SavedMatchesDao
}