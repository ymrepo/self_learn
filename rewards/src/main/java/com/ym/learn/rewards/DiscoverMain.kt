package com.ym.learn.rewards

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RewardsMain() {
    Scaffold { paddingValues -> RewardsContent(modifier = Modifier.padding(paddingValues)) }
}

@Composable
fun RewardsContent(modifier: Modifier) {
    Text("main content",modifier = modifier)
}