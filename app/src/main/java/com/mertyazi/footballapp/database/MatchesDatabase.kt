package com.mertyazi.footballapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mertyazi.footballapp.model.Matche

@Database(entities = [Matche::class], version = 5)
@TypeConverters(Converters::class)
abstract class MatchesDatabase: RoomDatabase() {

    abstract fun getMatchesDao(): MatchesDao

    companion object {
        @Volatile
        private var INSTANCE: MatchesDatabase? = null
        fun getDatabase(context: Context): MatchesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchesDatabase::class.java,
                    "matches_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}