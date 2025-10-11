package com.ym.learn.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ym.learn.ui.Loading
import kotlin.random.Random

@Composable
fun LoginPhoneScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()) {
    val state = loginViewModel.loginState.collectAsStateWithLifecycle()
    Log.i("Login", "LoginPhoneScreen....")
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        LaunchedEffect(Unit) {
            loginViewModel.navigationEvent.collect { event ->
                when (event) {
                    "home" -> {
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }
            }
        }
        LoginPhoneContent(
            isLoading = state.value.isLoading,
            phone = state.value.phone,
            maxValue = 13,
            loginOnClick = { loginViewModel.login(state.value.phone) },
            clearOnClick = { loginViewModel.updatePhone("") },
            onValueChanged = { value -> loginViewModel.updatePhone(value) },
            modifier = Modifier.padding(paddingValues)
        )
    }
}


@Composable
fun LoginPhoneContent(
    isLoading: Boolean,
    phone: String,
    maxValue: Int,
    loginOnClick: () -> Unit,
    clearOnClick: () -> Unit,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isError by remember { mutableStateOf(false) }
    var countryCode by remember { mutableStateOf("+86") }
    var showHalfCountryScreen by remember { mutableStateOf(false) }
    val controller = rememberNavController()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        OutlinedTextField(
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = phone,
            maxLines = 2,
            leadingIcon = {
                PhoneLeadIcon(
                    countryCode,
                    onClick = { showHalfCountryScreen = !showHalfCountryScreen })
            },
            placeholder = { Text(text = "Please input your phone") },
            textStyle = TextStyle(color = Color.DarkGray, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),

            trailingIcon = {
                if (phone.isNotEmpty()) {
                    IconButton(onClick = { clearOnClick.invoke() }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear Text")
                    }
                }
            },
            onValueChange = { value ->
                onValueChanged.invoke(value)
                isError = value.length > maxValue
            },
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { loginOnClick.invoke() },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }

    if (showHalfCountryScreen) {
        PartialBottomSheet(
            onDismissListener = { showHalfCountryScreen = false },
            onItemListener = { value, index ->
                countryCode = value
                showHalfCountryScreen = false
            })
    }
    if (isLoading) {
        Loading()
    }
}

fun getRandomColor() = Color(
    red = Random.nextInt(256), green = Random.nextInt(256), blue = Random.nextInt(256), alpha = 255
)


@Composable
fun PhoneLeadIcon(country: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.0.dp)
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = country)
        Icon(
            imageVector = Icons.Default.ArrowDropDown, contentDescription = "arrow"
        )
        VerticalDivider(
            color = Color.LightGray, modifier = Modifier
                .height(30.dp)       // 高度
                .width(1.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    onDismissListener: () -> Unit,
    onItemListener: (value: String, index: Int) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val data = remember {
        mutableStateListOf<String>().apply {
            addAll(List(10) { index -> "+12$index" })
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = {
                showBottomSheet = false
                onDismissListener.invoke()
            }
        ) {
            LazyColumn(modifier = Modifier) {
                items(data.size) { index ->
                    Text(
                        text = data[index], modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .clickable {
                                onItemListener.invoke(data[index], index)
                            })
                    if (index < data.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            thickness = 1.dp,
                            color = Color(0x1a000000)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagePhoneScreen() {
    LoginPhoneContent(
        isLoading = false,
        phone = "",
        maxValue = 13,
        loginOnClick = {},
        clearOnClick = {},
        onValueChanged = { value -> },
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HalfCountrySheet() {
    PartialBottomSheet(
        onDismissListener = {},
        onItemListener = { value, index -> }
    )
}
