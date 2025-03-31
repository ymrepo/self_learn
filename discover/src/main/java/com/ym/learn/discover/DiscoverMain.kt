package com.ym.discover

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DiscoverMain() {
    Scaffold { paddingValues -> DiscoverContent(modifier = Modifier.padding(paddingValues)) }
}

@Composable
fun DiscoverContent(modifier: Modifier) {
    Text("main content",modifier = modifier)
}