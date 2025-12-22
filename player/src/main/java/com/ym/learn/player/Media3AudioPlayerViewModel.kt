package com.ym.learn.player

import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Media3AudioPlayerViewModel : ViewModel() {

    // ExoPlayer 实例 (Media3)
    private val _player = mutableStateOf<ExoPlayer?>(null)
    val player: ExoPlayer? get() = _player.value

    // 播放状态
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    // 播放进度（毫秒）
    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition = _currentPosition.asStateFlow()

    // 音频总时长（毫秒）
    private val _duration = MutableStateFlow(0L)
    val duration = _duration.asStateFlow()

    // 初始化播放器
    fun initializePlayer(context: android.content.Context, audioUri: Uri) {
        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            // 创建 MediaItem 并设置给播放器
            val mediaItem = MediaItem.fromUri(audioUri)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = false // 初始不自动播放

            // 添加监听器来更新状态
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_READY -> {
                            _duration.value = duration.coerceAtLeast(0L)
                        }

                        Player.STATE_ENDED -> {
                            _isPlaying.value = false
                            // 播放结束时，可以选择重置进度或进行其他操作
                            seekTo(0)
                        }
                    }
                }

                override fun onIsPlayingChanged(playing: Boolean) {
                    _isPlaying.value = playing
                }

                override fun onEvents(player: Player, events: Player.Events) {
                    // 更新当前播放位置和总时长
                    _currentPosition.value = player.currentPosition
                    // 确保在播放器就绪后获取有效的 duration
                    if (player.duration != androidx.media3.common.C.TIME_UNSET) {
                        _duration.value = player.duration
                    }
                }
            })
        }
        _player.value = exoPlayer
    }

    // 播放/暂停控制
    fun playPause() {
        player?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.play()
            }
            // onIsPlayingChanged 监听器会更新 _isPlaying
        }
    }

    // 跳转到指定位置
    fun seekTo(positionMs: Long) {
        player?.seekTo(positionMs)
    }

    // 释放播放器资源
    fun releasePlayer() {
        player?.release()
        _player.value = null
        _isPlaying.value = false
        _currentPosition.value = 0L
        _duration.value = 0L
    }
}