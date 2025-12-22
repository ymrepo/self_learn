package com.ym.learn.rewards

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch

private val SAMPLE_ITEMS = listOf(
    "苹果",
    "香蕉",
    "橘子",
    "梨",
    "西瓜",
    "葡萄",
    "芒果",
    "草莓",
    "柚子",
    "菠萝",
    "111",
    "222",
    "333",
    "444",
    "555",
    "666",
    "777",
    "888",
    "999",
    "000",
    "1111",
    "2222",
    "3333",
    "4444",
    "5555",
    "6666",
    "7777",
    "8888",
    "9999",
    "0000", "11111",
    "22222",
)
private val TAB_LABELS = listOf("Available", "Used", "Expired")

val tabItems = arrayListOf<String>("tab1", "tab2", "tab3")

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RewardsMain() {

        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar( // Use LargeTopAppBar or MediumTopAppBar for collapsing effect
                    title = { Text("My Collapsing Toolbar") },
                    scrollBehavior = scrollBehavior,
                    // ... other parameters (navigationIcon, actions, colors)
                )
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                // ... other modifiers
            ) {
                items(count = 50) { index ->
                    Text("Item $index", modifier = Modifier.padding(16.dp))
                }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RewardsContent(modifier: Modifier, items: List<String>) {
    var query by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { items.size })
    val rewardPagerState = rememberPagerState(pageCount = { TAB_LABELS.size })

    // 创建一个可以记住滚动状态的滚动状态
    val scrollState = rememberScrollState()

    Log.i("RewardsMain", "Current query: $query")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // 轮播图
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(if (page % 2 == 0) R.color.purple_200 else R.color.purple_200)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = items[page])
            }
        }

        // TabRow
        TabRow(
            selectedTabIndex = rewardPagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .zIndex(1f) // 确保在滚动时保持在顶部
        ) {
            TAB_LABELS.forEachIndexed { index, label ->
                Tab(
                    selected = rewardPagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            rewardPagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(label) }
                )
            }
        }

        // Tab 内容区域
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp)
        ) {
            HorizontalPager(state = rewardPagerState) { page ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(items.size) { item ->
                        Text(
                            text = "Tab ${page}: ${item}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}