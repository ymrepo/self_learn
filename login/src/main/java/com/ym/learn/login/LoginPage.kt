package com.ym.learn.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ym.learn.ui.Loading
import kotlin.random.Random

@Composable
fun LoginPage(loginViewModel: LoginViewModel = viewModel()) {
    Log.i("Login", "recomposition LoginPage")
    val state = loginViewModel.loginState.collectAsState()
    Scaffold(modifier = Modifier) { paddingValues ->
        when (state.value) {
            LoginUiState.Loading -> {
                Loading()
            }

            LoginUiState.Error -> {

            }

            else -> {
                LoginForm(
                    modifier = Modifier.padding(paddingValues),
                    loginOnClick = { loginViewModel.login() })
            }
        }
    }
}

@Composable
fun Phone(
    phone: String,
    phoneOnValueChange: (String) -> Unit,
    phoneClearClick: () -> Unit,
) {
    Log.i("Login", "recomposition Phone")
    var isError by remember { mutableStateOf(false) }
    TextField(
        isError = isError,
        value = phone,
        maxLines = 2,
        leadingIcon = { PhoneLeadIcon() },
        placeholder = { Text(text = "Name") },
        textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(horizontal = 10.0.dp),

        trailingIcon = {
            if (phone.isNotEmpty()) {
                IconButton(onClick = { phoneClearClick.invoke() }) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear Text")
                }
            }
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = getRandomColor(),
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            unfocusedPlaceholderColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray
        ),

        onValueChange = {
            phoneOnValueChange.invoke(it)
            isError = it.length > 5
        },
    )
}

@Composable
fun LoginForm(
    modifier: Modifier,
    loginOnClick: () -> Unit
) {
    Log.i("Login", "recomposition LoginForm")
    val loginViewModel: LoginViewModel = viewModel()
    val phone by loginViewModel.phone.collectAsState()
    val sms by loginViewModel.sms.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Phone(phone,
            phoneOnValueChange = { loginViewModel.updatePhone(it) },
            phoneClearClick = { loginViewModel.updatePhone("") })
        TextField(
            singleLine = true,
            value = sms,
            placeholder = { Text(text = "please input sms") },
            onValueChange = { loginViewModel.updateSms(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = getRandomColor(),
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = getRandomColor()),
            onClick = { loginOnClick.invoke() }, modifier = Modifier
                .padding(vertical = 50.0.dp)
                .width(300.0.dp)
        ) {
            Log.i("Login", "recomposition Button")
            Text(text = "Login")
        }
    }

}

fun getRandomColor() = Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
    alpha = 255
)

@Preview
@Composable
fun PhoneLeadIcon() {
    Log.i("Login", "recomposition PhoneLeadIcon")
    Row(
        modifier = Modifier
            .padding(horizontal = 10.0.dp)
            .clickable { Log.i("Login", "recomposition PhoneLeadIcon") },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "+86")
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "arrow"
        )
        VerticalDivider(
            color = Color.LightGray,
            modifier = Modifier
                .height(30.dp)       // 高度
                .width(1.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PageAll() {
    LoginPage()
}
