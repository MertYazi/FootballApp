package com.mertyazi.footballapp.application

import android.app.Application
import com.mertyazi.footballapp.database.MatchesDatabase
import com.mertyazi.footballapp.database.MatchesRepository

class MatchesApplication: Application() {

    private val database by lazy { MatchesDatabase.getDatabase(this@MatchesApplication) }

    val repository by lazy { MatchesRepository(database.getMatchesDao()) }
}