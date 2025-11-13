package com.ym.learn.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ym.learn.ui.YmButtonStyle
import com.ym.learn.ui.YmFilledButton
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ym.learn.login.viewmodel.LoginPinViewModel
import com.ym.learn.ui.YmBoxTextField
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginPinPage(
    navController: NavController,
    viewModel: LoginPinViewModel = hiltViewModel<LoginPinViewModel>()
) {
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest {
            if (it == "success") {
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
    Scaffold(modifier = Modifier) { paddingValues ->
        LoingPinContent(paddingValues)
    }
}

@Composable
fun LoingPinContent(
    paddingValues: PaddingValues,
    viewModel: LoginPinViewModel = hiltViewModel<LoginPinViewModel>()
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("请输入PIN", fontSize = 18.sp, modifier = Modifier.padding(bottom = 24.dp))
        YmBoxTextField()
        YmFilledButton(style = YmButtonStyle.Filled, text = "Next", onClick = {
            viewModel.doLoginWithPin()
        })
    }
}

@Composable
@Preview
fun ExampleUsage() {
    LoingPinContent(paddingValues = PaddingValues(10.dp))
}