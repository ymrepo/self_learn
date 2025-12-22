//package com.ym.learn.rewards
//
//import android.util.Log
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//
//
//private val SAMPLE_ITEMS = listOf(
//    "苹果",
//    "香蕉",
//    "橘子",
//    "梨",
//    "西瓜",
//    "葡萄",
//    "芒果",
//    "草莓",
//    "柚子",
//    "菠萝"
//)
//
//@Composable
//fun RewardsMain() {
//    Scaffold { paddingValues ->
//        RewardsContent(modifier = Modifier.padding(paddingValues))
//    }
//}
//
//@Composable
//fun RewardsContent(modifier: Modifier) {
//    var query by remember { mutableStateOf("") }
//    Log.i("RewardsMain", "Current query: $query")
//    Column(modifier = modifier.padding(16.dp)) {
//        OutlinedTextField(
//            value = query,
//            onValueChange = { query = it },
//            modifier = Modifier
//                .fillMaxWidth(),
//            placeholder = { Text(text = "搜索...") },
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = Color(0xFFBDBDBD), // 浅灰
//                focusedBorderColor = Color(0xFF424242),   // 深灰
//                cursorColor = Color.Black
//            )
//        )
//
//        val filtered = if (query.isBlank()) SAMPLE_ITEMS else {
//            SAMPLE_ITEMS.filter { it.contains(query, ignoreCase = true) }
//        }
//
//        LazyColumn(modifier = Modifier.padding(top = 12.dp)) {
//            items(filtered.size) { item ->
//                Text(
//                    text = filtered[item],
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 12.dp)
//                )
//            }
//        }
//    }
//}