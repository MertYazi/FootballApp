package com.mertyazi.footballapp.shared.business.repository

import com.mertyazi.footballapp.shared.business.entities.Matche
import com.mertyazi.footballapp.shared.data.repository.api.Result

interface SavedMatchesRepository {

    suspend fun getSavedMatches(): Result<List<Matche>>

    suspend fun addToSavedMatches(match : Matche)

    suspend fun removeFromSavedMatches(match : Matche)

}