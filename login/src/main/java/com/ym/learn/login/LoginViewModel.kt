package com.ym.learn.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ym.learn.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val myModelRepository: LoginRepository = LoginRepository()
    private val _loginState =
        MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> =
        _loginState.asStateFlow()

    // 用于导航的事件（使用SharedFlow，一次性事件）
    private val _navigationEvent = MutableSharedFlow<String>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun updatePhone(phone: String) {
        _loginState.update {
            it.copy(phone = phone)
        }
    }

    fun login(phone: String) {
        viewModelScope.launch {
            _loginState.update { it.copy(isLoading = true) }
            val result = myModelRepository.login(phone)
            _loginState.update { it.copy(isLoading = false, isSuccess = true) }
            _navigationEvent.emit("home")
        }
    }
}

data class LoginUiState(
    val phone: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)

