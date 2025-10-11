package com.ym.learn.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ym.learn.data.model.People
import com.ym.learn.ui.Error
import com.ym.learn.ui.Loading
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeMain(viewModel: HomeViewModel = viewModel()) {
    val state = viewModel.peopleStateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        if (state.value !is HomeUiState.Success) {
            viewModel.refreshWithLoading(1)
        }
    }
    Scaffold(topBar = { TopAppBar(title = { Text("Main page") }) }) { paddingValues ->
        when (state.value) {
            is HomeUiState.Error -> Error()
            is HomeUiState.Loading -> Loading()
            is HomeUiState.Success ->
                MainContent(
                    modifier = Modifier.padding(paddingValues),
                    isRefreshing = (state.value as HomeUiState.Success).isRefreshing,
                    items = (state.value as HomeUiState.Success).peoples ?: arrayListOf(),
                    onRefreshing = {
                        scope.launch {
                            viewModel.getPeople(1)
                        }
                    }
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    modifier: Modifier,
    isRefreshing: Boolean,
    items: List<People>,
    onRefreshing: () -> Unit
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = {
            onRefreshing.invoke()
        },
        modifier = modifier
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .background(color = colorResource(R.color.yellow))
            )
            LazyColumn(Modifier.fillMaxSize()) {
                items(items.size, key = { index -> items[index].name }) { index ->
                    ListItem({ Text(text = "item:${items[index].name}") })
                }
            }
        }
    }
}

