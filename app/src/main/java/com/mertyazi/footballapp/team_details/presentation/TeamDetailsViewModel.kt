package com.mertyazi.footballapp.team_details.presentation

import androidx.lifecycle.*
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.shared.business.repository.MatchesRepository
import com.mertyazi.footballapp.shared.data.repository.api.Result
import com.mertyazi.footballapp.shared.presentation.DateFormatter
import com.mertyazi.footballapp.shared.business.mapper.MatcheToMatcheViewStateMapper
import com.mertyazi.footballapp.team_details.business.usecase.AddToSavedMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    private val repository: MatchesRepository,
    private val addToSavedMatchesUseCase: AddToSavedMatchesUseCase,
    private val matcheToMatcheViewStateMapper: MatcheToMatcheViewStateMapper,
    private val dateFormatter: DateFormatter,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateTeamDetails = MutableLiveData<TeamDetailsViewState>()
    val viewStateTeamDetails: LiveData<TeamDetailsViewState>
        get() = _viewStateTeamDetails

    fun getMatchDetails(id: Int) = viewModelScope.launch(dispatcher) {
        _viewStateTeamDetails.postValue(TeamDetailsViewState.Loading)
        while (true) {
            when (val result = repository.getTeamDetails(id)) {
                is Result.Error -> {
                    _viewStateTeamDetails.postValue(TeamDetailsViewState.Error)
                }
                is Result.Success -> {
                    val matches = result.data.matches.map {
                        it.utcDate = dateFormatter.format(it.utcDate, "")
                        matcheToMatcheViewStateMapper.invoke(it)
                    }.toMutableList()
                    _viewStateTeamDetails.postValue(TeamDetailsViewState.ContentTeamDetails(
                        matches
                    ))
                }
            }
            delay(Constants.REFRESHING_TIME_TEAM_DETAILS)
        }
    }

    fun saveMatchClicked(match: MatcheViewState) {
        viewModelScope.launch(dispatcher) {
            addToSavedMatchesUseCase.execute(match)
            val currentViewState = _viewStateTeamDetails.value
            (currentViewState as? TeamDetailsViewState.ContentTeamDetails)?.let { content ->
                _viewStateTeamDetails.postValue(
                    content.saveMatch(match)
                )
            }
        }
    }
}