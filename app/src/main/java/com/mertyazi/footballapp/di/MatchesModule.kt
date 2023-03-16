package com.mertyazi.footballapp.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.mertyazi.footballapp.league_table.business.mapper.LeagueTableEntityToLeagueTableMapper
import com.mertyazi.footballapp.shared.business.mapper.CompetitionXEntityToCompetitionXMapper
import com.mertyazi.footballapp.shared.business.mapper.MatcheEntityToMatcheMapper
import com.mertyazi.footballapp.shared.business.repository.MatchesRepository
import com.mertyazi.footballapp.shared.business.repository.SavedMatchesRepository
import com.mertyazi.footballapp.shared.business.repository.database.SavedMatchesDatabase
import com.mertyazi.footballapp.shared.data.repository.SavedMatchesDatabaseRepository
import com.mertyazi.footballapp.shared.data.repository.api.ApiClient
import com.mertyazi.footballapp.shared.data.repository.api.MatchesRepositoryAPI
import com.mertyazi.footballapp.shared.data.repository.api.MatchesService
import com.mertyazi.footballapp.shared.data.repository.database.SavedMatchesDao
import com.mertyazi.footballapp.shared.presentation.CoilImageLoader
import com.mertyazi.footballapp.shared.presentation.DateFormatter
import com.mertyazi.footballapp.shared.presentation.DateFormatterForTR
import com.mertyazi.footballapp.shared.presentation.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class MatchesModule {

    @Provides
    fun providesMatchesService(): MatchesService = ApiClient.getService()

    @Provides
    fun providesMatchesRepositoryAPI(
        service: MatchesService,
        matcheEntityToMatcheMapper: MatcheEntityToMatcheMapper,
        competitionXEntityToCompetitionXMapper: CompetitionXEntityToCompetitionXMapper,
        leagueTableEntityToLeagueTableMapper: LeagueTableEntityToLeagueTableMapper
    ): MatchesRepositoryAPI = MatchesRepositoryAPI(
        service,
        matcheEntityToMatcheMapper,
        competitionXEntityToCompetitionXMapper,
        leagueTableEntityToLeagueTableMapper)

    @Provides
    fun providesMatchesRepository(
        matchesRepositoryAPI: MatchesRepositoryAPI
    ): MatchesRepository = matchesRepositoryAPI

    @Provides
    fun providesSavedMatchesRepository(
        databaseRepository: SavedMatchesDatabaseRepository
    ): SavedMatchesRepository = databaseRepository

    @Provides
    fun providesSavedMatchesDatabaseRepository(databaseDAO: SavedMatchesDao
    ): SavedMatchesDatabaseRepository {
        return SavedMatchesDatabaseRepository(databaseDAO)
    }

    @Provides
    fun providesMatchesDAO(
        @ApplicationContext context: Context
    ): SavedMatchesDao {
        val db = Room.databaseBuilder(
            context, SavedMatchesDatabase::class.java,
            "matches-database"
        ).build()
        return db.matchesDao()
    }

    @Provides
    @Singleton
    fun providesDateFormatter(): DateFormatter {
        return DateFormatterForTR
    }

    @Provides
    @Singleton
    fun providesImageLoader(): ImageLoader {
        return CoilImageLoader
    }

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}