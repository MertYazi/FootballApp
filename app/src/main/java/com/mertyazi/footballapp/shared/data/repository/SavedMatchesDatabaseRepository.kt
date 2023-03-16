package com.mertyazi.footballapp.shared.data.repository

import com.mertyazi.footballapp.shared.business.entities.Matche
import com.mertyazi.footballapp.shared.business.repository.SavedMatchesRepository
import com.mertyazi.footballapp.shared.data.repository.database.SavedMatchesDao
import com.mertyazi.footballapp.shared.data.repository.api.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavedMatchesDatabaseRepository @Inject constructor(
    private val databaseDAO: SavedMatchesDao
) : SavedMatchesRepository {
    override suspend fun getSavedMatches(): Result<List<Matche>> {
        return withContext(Dispatchers.IO) {
            Result.Success(databaseDAO.getAllMatches())
        }
    }

    override suspend fun addToSavedMatches(match: Matche) {
        return withContext(Dispatchers.IO) {
            databaseDAO.insertMatch(
                match
            )
        }
    }

    override suspend fun removeFromSavedMatches(match: Matche) {
        return withContext(Dispatchers.IO) {
            databaseDAO.deleteMatch(
                match
            )
        }
    }
}