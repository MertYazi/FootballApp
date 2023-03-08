package com.mertyazi.footballapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mertyazi.footballapp.database.MatchesRepository
import com.mertyazi.footballapp.model.CompetitionX
import com.mertyazi.footballapp.model.Matche
import com.mertyazi.footballapp.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MatchesViewModel(
    app: Application,
    private val repository: MatchesRepository
): AndroidViewModel(app) {

    private var mCode: String = ""
    private var mMatchID: Int = 0
    val leagues: MutableLiveData<List<CompetitionX>> = MutableLiveData()

    val matchesToday = liveData {
        while (true) {
            if(Constants.checkConnection(this@MatchesViewModel)) {
                repository.getMatchesToday().body()?.let {
                    emit(it.matches)
                }
            }
            delay(Constants.REFRESHING_TIME)
        }
    }

    val leagueTable = liveData {
        while (true) {
            if(Constants.checkConnection(this@MatchesViewModel)) {
                repository.getLeagueTable(mCode).body()?.let {
                    emit(it.standings)
                }
            }
            delay(Constants.REFRESHING_TIME)
        }
    }

    fun getLeagues(availableLeagues: ArrayList<String>, allLeagues: String) = viewModelScope.launch {
        if(Constants.checkConnection(this@MatchesViewModel)) {
            repository.getLeagues().body()?.let {
                val allLeaguesItem = CompetitionX()
                allLeaguesItem.emblem = ""
                allLeaguesItem.name = Constants.ALL_LEAGUES
                val list = it.competitions.toMutableList()
                if (allLeagues == Constants.ALL_LEAGUES) {
                    val todayList = mutableListOf<CompetitionX>()
                    for (listItem in list) {
                        if (availableLeagues.contains(listItem.name)) {
                            todayList.add(listItem)
                        }
                    }
                    todayList.add(0, allLeaguesItem)
                    leagues.postValue(todayList)
                } else {
                    leagues.postValue(list)
                }
            }
        }
    }

    val matches = liveData {
        while (true) {
            if(Constants.checkConnection(this@MatchesViewModel)) {
                repository.getMatches(mMatchID).body()?.let {
                    emit(it.matches)
                }
            }
            delay(Constants.REFRESHING_TIME_MATCHES)
        }
    }

    fun getLeagueTable(code: String) {
        mCode = code
    }

    fun getMatches(id: Int) {
        mMatchID = id
    }

    fun saveMatch(match: Matche) = viewModelScope.launch {
        repository.insert(match)
    }

    fun getSavedMatches() = repository.getSavedMatches()

    fun deleteSavedMatch(match: Matche) = viewModelScope.launch {
        repository.deleteMatch(match)
    }

}

class MatchesViewModelFactory(
    val app: Application,
    private val repository: MatchesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
            return MatchesViewModel(app, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}