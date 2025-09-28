package com.ym.learn.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ym.learn.data.model.People
import com.ym.learn.data.repository.HomeRepository
import com.ym.learn.data.repository.LoginRepository
import com.ym.learn.ui.Loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val myModelRepository: HomeRepository = HomeRepository()
    private val _peopleStateFlow =
        MutableStateFlow<HomeUiState>(HomeUiState.Loading())
    val peopleStateFlow: StateFlow<HomeUiState> =
        _peopleStateFlow.asStateFlow()

    private val peoples = mutableListOf<People>()

    fun getPeople(page: Int) {
        viewModelScope.launch {
            val result = myModelRepository.getPeople(page)
            if (result.results?.isNotEmpty() == true) {
                if (page == 1) {
                    peoples.clear()
                    peoples.addAll(result.results ?: arrayListOf())
                    _peopleStateFlow.emit(HomeUiState.Success(peoples = peoples))
                } else {
                    peoples.addAll(result.results ?: arrayListOf())
                    _peopleStateFlow.emit(HomeUiState.Success(peoples = peoples))
                }
            }
        }
    }

    fun refreshWithLoading() {
        viewModelScope.launch {
            _peopleStateFlow.emit(HomeUiState.Loading(isLoading = true))
            val result = myModelRepository.getPeople(1)
            _peopleStateFlow.emit(HomeUiState.Loading(isLoading = false))
            if (result.results?.isNotEmpty() == true) {
                _peopleStateFlow.emit(
                    HomeUiState.Success(
                        peoples = result.results,
                        hasMore = result.count > 10
                    )
                )
            } else {
                _peopleStateFlow.emit(HomeUiState.Error())
            }
        }
    }
}

sealed class HomeUiState {
    data class Success(
        val peoples: List<People>? = null,
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val isSuccess: Boolean = false,
        val hasMore: Boolean = true
    ) : HomeUiState()

    data class Error(
        val errorCode: String = "",
        val errorMessage: String = "",
    ) : HomeUiState()

    data class Loading(
        val isLoading: Boolean = false,
    ) : HomeUiState()
}


