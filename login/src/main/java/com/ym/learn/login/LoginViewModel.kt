package com.ym.learn.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ym.learn.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
//    var phone by mutableStateOf("")
//        private set
//    var sms by mutableStateOf("")
//        private set

    // LoginViewModel.kt
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _sms = MutableStateFlow("")
    val sms: StateFlow<String> = _sms.asStateFlow()

    private val myModelRepository: LoginRepository = LoginRepository()
    private val _loginState =
        MutableStateFlow<LoginUiState>(LoginUiState.IDLE)
    val loginState = _loginState.asStateFlow()

    fun updatePhone(newPhone: String) {
        _phone.value = newPhone
    }

    fun updateSms(newSms: String) {
        _sms.value = newSms
    }

    fun login() {
        Log.i("Login", "doLogin:${phone.value}")
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            val result = myModelRepository.login(_phone.value)
            _loginState.value =
                if (result.isNotEmpty()) LoginUiState.Success else LoginUiState.Error
            Log.i("Login", "httpResult:${result}")
        }
    }
}

sealed interface LoginUiState {
    data object IDLE : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data object Error : LoginUiState
}