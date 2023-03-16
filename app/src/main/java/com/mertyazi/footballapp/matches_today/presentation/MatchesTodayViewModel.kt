package com.mertyazi.footballapp.matches_today.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.Constants.TODAY
import com.mertyazi.footballapp.shared.business.mapper.CompetitionXToCompetitionXViewStateMapper
import com.mertyazi.footballapp.shared.presentation.CompetitionXViewState
import com.mertyazi.footballapp.shared.business.repository.MatchesRepository
import com.mertyazi.footballapp.shared.data.repository.api.Result
import com.mertyazi.footballapp.shared.presentation.DateFormatter
import com.mertyazi.footballapp.shared.business.mapper.MatcheToMatcheViewStateMapper
import com.mertyazi.footballapp.team_details.presentation.MatchesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesTodayViewModel @Inject constructor(
    private val repository: MatchesRepository,
    private val matcheToMatcheViewStateMapper: MatcheToMatcheViewStateMapper,
    private val competitionXToCompetitionXViewStateMapper: CompetitionXToCompetitionXViewStateMapper,
    private val dateFormatter: DateFormatter,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateMatchesToday = MutableLiveData<MatchesTodayViewState>()
    val viewStateMatchesToday: LiveData<MatchesTodayViewState>
        get() = _viewStateMatchesToday

    private val _viewStateLeagues = MutableLiveData<MatchesTodayViewState>()
    val viewStateLeagues: LiveData<MatchesTodayViewState>
        get() = _viewStateLeagues

    fun getMatchesToday() = viewModelScope.launch(dispatcher) {
        _viewStateMatchesToday.postValue(MatchesTodayViewState.Loading)
        while (true) {
            when (val result = repository.getMatchesToday()) {
                is Result.Error -> {
                    _viewStateMatchesToday.postValue(MatchesTodayViewState.Error)
                }
                is Result.Success -> {
                    val matches = MatchesViewState(
                        result.data.matches.map {
                            it.utcDate = dateFormatter.format(it.utcDate, TODAY)
                            matcheToMatcheViewStateMapper.invoke(it)
                        }.toMutableList()
                    )
                    _viewStateMatchesToday.postValue(
                        MatchesTodayViewState.ContentMatchesToday(
                        matches
                    ))
                }
            }
            delay(Constants.REFRESHING_TIME_MATCHES_TODAY)
        }
    }

    fun getLeagues(availableLeagues: ArrayList<String>) = viewModelScope.launch(dispatcher) {
        _viewStateMatchesToday.postValue(MatchesTodayViewState.Loading)
        while (true) {
            when (val result = repository.getLeagues()) {
                is Result.Error -> {
                    _viewStateLeagues.postValue(MatchesTodayViewState.Error)
                }
                is Result.Success -> {
                    val itemAllLeagues = CompetitionXViewState()
                    itemAllLeagues.emblem = ""
                    itemAllLeagues.name = Constants.ALL_LEAGUES
                    val list = result.data.competitions.map { league ->
                        competitionXToCompetitionXViewStateMapper(league)
                    }.toMutableList()

                    val todayLeaguesList = mutableListOf<CompetitionXViewState>()
                    for (listItem in list) {
                        if (availableLeagues.contains(listItem.name)) {
                            todayLeaguesList.add(listItem)
                        }
                    }
                    todayLeaguesList.add(0, itemAllLeagues)
                    _viewStateLeagues.postValue(
                        MatchesTodayViewState.ContentLeagues(
                            todayLeaguesList
                        ))
                }
            }
            delay(Constants.REFRESHING_TIME_LEAGUES)
        }
    }
}