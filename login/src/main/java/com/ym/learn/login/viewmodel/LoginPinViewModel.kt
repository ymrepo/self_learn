package com.ym.learn.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ym.learn.data.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginPinViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _action = MutableSharedFlow<String>()
    val action: SharedFlow<String> = _action.asSharedFlow()
    fun doLoginWithPin() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.login("35435")
            _action.emit("success")
        }
    }
}