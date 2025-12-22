package com.ym.learn.player

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.time.Duration.Companion.milliseconds

@Composable
@Preview
fun previewComposable() {
    Media3AudioPlayerComposable("")
}

@Composable
fun Media3AudioPlayerComposable(
    audioUri: String, // 音频文件的 URI，可以是网络 URL 或本地 URI
    viewModel: Media3AudioPlayerViewModel = viewModel()
) {
    val context = LocalContext.current

    // 从 ViewModel 收集状态
    val isPlaying by viewModel.isPlaying.collectAsState(initial = false)
    val currentPosition by viewModel.currentPosition.collectAsState(initial = 0L)
    val duration by viewModel.duration.collectAsState(initial = 0L)

    // 在初始组合时初始化播放器，或当 audioUri 变化时重新初始化
    LaunchedEffect(audioUri) {
        viewModel.initializePlayer(context, Uri.parse(audioUri))
    }

    // 处理生命周期：当 Composable 离开组合时释放播放器
    DisposableEffect(Unit) {
        onDispose {
            viewModel.releasePlayer()
        }
    }

    // UI 布局
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 进度条和时间的行
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 当前时间
            Text(
                text = formatMillisToTime(currentPosition), textAlign = TextAlign.Right
            )

            Spacer(modifier = Modifier.width(8.dp))

            // 进度条滑块
            var sliderPosition by remember(currentPosition) { mutableFloatStateOf(currentPosition.toFloat()) }
            var isSliding by remember { mutableStateOf(false) } // 标记用户是否正在拖动滑块

            Slider(
                value = if (isSliding) sliderPosition else currentPosition.toFloat(),
                onValueChange = { newValue ->
                    isSliding = true
                    sliderPosition = newValue
                    // 这里可以实时更新一个预览时间，但不真正跳转播放位置
                },
                onValueChangeFinished = {
                    isSliding = false
                    // 当用户结束拖动时，执行跳转
                    viewModel.seekTo(sliderPosition.toLong())
                },
                valueRange = 0f..duration.toFloat().coerceAtLeast(1f), // 避免除零
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // 总时长
            Text(
                text = formatMillisToTime(duration)
            )
            IconButton(onClick = { viewModel.playPause() }) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )

            }
        }
    }
}


// 辅助函数：将毫秒格式化为 "MM:SS" 或 "HH:MM:SS" 字符串
private fun formatMillisToTime(millis: Long): String {
    val duration = millis.milliseconds
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    val seconds = duration.inWholeSeconds % 60

    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}