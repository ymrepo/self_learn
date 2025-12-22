package com.ym.learn.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ym.learn.data.model.People
import com.ym.learn.ui.Error
import com.ym.learn.ui.Loading
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeMain(viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()) {
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
            val pagerState = rememberPagerState(pageCount = { items.size })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(if (page % 2 == 0) R.color.yellow else R.color.purple_200)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = items[page].name)
                }
            }
            LazyColumn(Modifier.fillMaxSize()) {
                items(items.size, key = { index -> items[index].name }) { index ->
                    ListItem({ Text(text = "item:${items[index].name}") })
                }
            }
        }
    }
}
