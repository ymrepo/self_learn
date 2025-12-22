package com.ym.learn.player

import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ym.learn.ui.DynamicAsyncImage
import kotlin.time.Duration.Companion.milliseconds


@Composable
@Preview
fun PreviewGlobalAudioPlayer() {
    GlobalAudioPlayer(title = "sldjflskdjflsjdlkfj")
}

@Composable
fun GlobalAudioPlayer(
    audioUri: String = "", // 音频文件的 URI，可以是网络 URL 或本地 URI
    title: String = "",
    modifier: Modifier = Modifier,
    viewModel: Media3AudioPlayerViewModel = viewModel()
) {
    val context = LocalContext.current

    // 从 ViewModel 收集状态
    val isPlaying by viewModel.isPlaying.collectAsState(initial = false)
    val currentPosition by viewModel.currentPosition.collectAsState(initial = 0L)
    val duration by viewModel.duration.collectAsState(initial = 0L)

    // 计算播放进度百分比
    val progress = if (duration > 0) {
        (currentPosition.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colorResource(com.ym.learn.data.R.color.white))
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DynamicAsyncImage(
            "", modifier = Modifier
                .width(50.dp)
                .height(50.dp), contentDescription = "cover"
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title)
            // 当前时间
            Row {
                Text(
                    textAlign = TextAlign.Left,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    text = formatMillisToTime(currentPosition),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(text = "/", modifier = Modifier.padding(4.dp))
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = formatMillisToTime(duration)
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            // 圆形进度条
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(56.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            // 播放/暂停按钮
            IconButton(
                onClick = { viewModel.playPause() },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.primary
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