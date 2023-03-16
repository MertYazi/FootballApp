package com.mertyazi.footballapp.leagues.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertyazi.footballapp.shared.business.mapper.CompetitionXToCompetitionXViewStateMapper
import com.mertyazi.footballapp.shared.business.repository.MatchesRepository
import com.mertyazi.footballapp.shared.data.repository.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaguesViewModel @Inject constructor(
    private val repository: MatchesRepository,
    private val competitionXToCompetitionXViewStateMapper: CompetitionXToCompetitionXViewStateMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateLeagues = MutableLiveData<LeaguesViewState>()
    val viewStateLeagues: LiveData<LeaguesViewState>
        get() = _viewStateLeagues

    fun getLeagues() = viewModelScope.launch(dispatcher) {
        _viewStateLeagues.postValue(LeaguesViewState.Loading)
        when (val result = repository.getLeagues()) {
            is Result.Error -> {
                _viewStateLeagues.postValue(LeaguesViewState.Error)
            }
            is Result.Success -> {
                val leagues = result.data.competitions.map { league ->
                    competitionXToCompetitionXViewStateMapper(league)
                }.toMutableList()
                _viewStateLeagues.postValue(
                    LeaguesViewState.ContentLeagues(
                        leagues
                    ))
            }
        }
    }
}