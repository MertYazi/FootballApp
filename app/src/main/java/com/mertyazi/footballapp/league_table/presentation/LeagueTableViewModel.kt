package com.mertyazi.footballapp.league_table.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mertyazi.footballapp.Constants
import com.mertyazi.footballapp.league_table.business.mapper.TableToTableViewStateMapper
import com.mertyazi.footballapp.shared.business.repository.MatchesRepository
import com.mertyazi.footballapp.shared.data.repository.api.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(
    private val repository: MatchesRepository,
    private val tableToTableViewStateMapper: TableToTableViewStateMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _viewStateLeagueTable = MutableLiveData<LeagueTableViewState>()
    val viewStateLeagueTable: LiveData<LeagueTableViewState>
        get() = _viewStateLeagueTable

    fun getLeagueTable(code: String) = viewModelScope.launch(dispatcher) {
        _viewStateLeagueTable.postValue(LeagueTableViewState.Loading)
        while (true) {
            when (val result = repository.getLeagueTable(code)) {
                is Result.Error -> {
                    _viewStateLeagueTable.postValue(LeagueTableViewState.Error)
                }
                is Result.Success -> {
                    val table = result.data.standings.map { standing ->
                        StandingViewState(
                            standing.stage,
                            standing.table.map { table ->
                                tableToTableViewStateMapper.invoke(table)
                            }
                        )
                    }.toMutableList()
                    _viewStateLeagueTable.postValue(
                        LeagueTableViewState.ContentLeagueTable(
                        table
                    ))
                }
            }
            delay(Constants.REFRESHING_TIME_LEAGUE_TABLE)
        }
    }
}