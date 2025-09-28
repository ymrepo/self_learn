package com.ym.learn.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ym.learn.data.model.People
import com.ym.learn.ui.Error
import com.ym.learn.ui.Loading
import kotlinx.coroutines.launch

@Composable
fun HomeMain(viewModel: HomeViewModel = viewModel()) {
    val state = viewModel.peopleStateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        viewModel.refreshWithLoading()
    }
    Scaffold { paddingValues ->
        when (state.value) {
            is HomeUiState.Error -> Error()
            is HomeUiState.Loading -> Loading()
            is HomeUiState.Success -> MainContent(
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


    PullToRefreshBox(
        isRefreshing = isRefreshing, onRefresh = {
            onRefreshing.invoke()
        }, modifier = modifier
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(items.size, key = { index -> items[index].name }) { index ->
                ListItem({ Text(text = "item:${items[index].name}") })
            }
        }
    }
}

fun onRefresh() {

}
