package com.ym.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeMain() {
    Scaffold { paddingValues -> MainContent(modifier = Modifier.padding(paddingValues)) }
}

@Composable
fun MainContent(modifier: Modifier) {
    Text("main content",modifier = modifier)
}
