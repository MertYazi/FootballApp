package com.mertyazi.footballapp.saved_matches.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertyazi.footballapp.saved_matches.business.usecase.DeleteFromSavedMatchesUseCase
import com.mertyazi.footballapp.shared.data.repository.api.Result
import com.mertyazi.footballapp.shared.business.repository.SavedMatchesRepository
import com.mertyazi.footballapp.shared.business.mapper.MatcheToMatcheViewStateMapper
import com.mertyazi.footballapp.team_details.presentation.MatcheViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMatchesViewModel @Inject constructor(
    private val repository: SavedMatchesRepository,
    private val deleteFromSavedMatchesUseCase: DeleteFromSavedMatchesUseCase,
    private val matcheToMatcheViewStateMapper: MatcheToMatcheViewStateMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateSavedMatches = MutableLiveData<SavedMatchesViewState>()
    val viewStateSavedMatches: LiveData<SavedMatchesViewState>
        get() = _viewStateSavedMatches

    fun getSavedMatches() = viewModelScope.launch(dispatcher) {
        _viewStateSavedMatches.postValue(SavedMatchesViewState.Loading)
        when (val result = repository.getSavedMatches()) {
            is Result.Error -> _viewStateSavedMatches.postValue(SavedMatchesViewState.Error)
            is Result.Success -> {
                val savedMatches = result.data.map {
                    matcheToMatcheViewStateMapper.invoke(it)
                }.toMutableList()
                _viewStateSavedMatches.postValue(SavedMatchesViewState.ContentSavedMatches(
                    savedMatches
                ))
            }
        }
    }

    fun deleteMatchClicked(match: MatcheViewState) {
        viewModelScope.launch(dispatcher) {
            deleteFromSavedMatchesUseCase.execute(match)
            val currentViewState = _viewStateSavedMatches.value
            (currentViewState as? SavedMatchesViewState.ContentSavedMatches)?.let { content ->
                _viewStateSavedMatches.postValue(
                    content.deleteMatch(match)
                )
            }
        }
    }
}