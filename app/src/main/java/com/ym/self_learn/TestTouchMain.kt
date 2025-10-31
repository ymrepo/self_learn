package com.ym.self_learn

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
@Composable
fun TestTouchMain() {
    var log by remember { mutableStateOf("") }
    var eventCount = 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        log += "P-I${event.changes.first().type},"
                        eventCount++
                        event.changes.forEach {
                            it.consume()
                            log += "消费[${it.id}]→"
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent(PointerEventPass.Main)
                        log += "P-M${event.changes.first().type},"
                    }
                }
            }.   pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent(PointerEventPass.Final)
                    log += "P-F${event.changes.first().type},"
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.Center)
                .background(Color.Red)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Initial)
                            log += "C-I${event.changes.first().type},"
                        }
                    }
                }
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Main)
                            log += "C-M${event.changes.first().type},"
                        }
                    }
                } .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent(PointerEventPass.Final)
                            log += "C-F${event.changes.first().type},"
                        }
                    }
                }.pointerInput(Unit){
                    detectTapGestures {  log += "tap child"}
                }
        ){
            Text(log, modifier = Modifier.align(Alignment.Center))
        }
    }
}
//@Composable
//fun TestTouchMain() {
//    var log by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Gray)
//            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent(PointerEventPass.Initial)
//                        log += "P-I${event.changes.first().id},"
//                    }
//                }
//            }
//            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent(PointerEventPass.Main)
//                        log += "P-Main,"
//                    }
//                }
//            }
//    ) {
//        Box(
//            modifier = Modifier
//                .size(400.dp) .align(Alignment.Center)
//                .background(Color.Red)
//                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent(PointerEventPass.Initial)
//                            log += "C-Initial,"
//                        }
//                    }
//                }
//                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent(PointerEventPass.Main)
//                            log += "C-Main,"
//                        }
//                    }
//                }
//        ){
//            Text(log, modifier = Modifier.align(Alignment.Center))
//        }
//    }
//}
//@Composable
//fun TestTouchMain() {
//    var log by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent(PointerEventPass.Initial)
//                        log += "父Initial→"
//                        // 关键：我们在这里消费事件
//                        event.changes.forEach {
//                            it.consume()
//                            log += "消费[${it.id}]→"
//                        }
//                    }
//                }
//            }
//            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent(PointerEventPass.Main)
//                        log += "父Main→"
//                        // 检查事件是否已被消费
//                        if (event.changes.any { it.isConsumed }) {
//                            log += "父看到已消费→"
//                        }
//                    }
//                }
//            }
//    ) {
//        Box(
//            modifier = Modifier
//                .size(400.dp)
//                .background(Color.Blue)
//                .align(Alignment.Center)
//                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent(PointerEventPass.Initial)
//                            log += "子Initial→"
//                            // 检查子节点看到的事件状态
//                            if (event.changes.any { it.isConsumed }) {
//                                log += "子看到已消费→"
//                            }
//                        }
//                    }
//                }
//                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent(PointerEventPass.Main)
//                            log += "子Main→"
//                            if (event.changes.any { it.isConsumed }) {
//                                log += "子Main看到已消费→"
//                            }
//                        }
//                    }
//                }
//                .pointerInput(Unit) {
//                    detectTapGestures {
//                        log += "子点击成功→" // 这个应该不会执行
//                    }
//                }
//        ) {
//            Text(log, modifier = Modifier.align(Alignment.Center))
//        }
//    }
//}

//@Composable
//fun TestTouchMain() {
//    var log by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)
//            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent(PointerEventPass.Initial)
//                        log += "父Initial→"
//                        event.changes.forEach { it.consume() }
//                        log += "父消费→"
//                    }
//                }
//            }
//            .pointerInput(Unit) {
//                awaitPointerEventScope {
//                    while (true) {
//                        val event = awaitPointerEvent(PointerEventPass.Main)
//                        log += "父Main→" // 这个会执行
//                    }
//                }
//            }
//    ) {
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .background(Color.Blue)
//                .align(Alignment.Center)
//                .pointerInput(Unit) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent(PointerEventPass.Initial)
//                            log += "子Initial→" // 🚫 永远不会执行
//                        }
//                    }
//                }
//                .pointerInput(Unit) {
//                    detectTapGestures {
//                        log += "子点击→" // 🚫 永远不会执行
//                    }
//                }
//        ) {
//            Text(log, modifier = Modifier.align(Alignment.Center))
//        }
//    }
//}
//@Composable
//fun TestTouchMain() {
//    Box(
//        modifier = Modifier
//            .size(200.dp)
//            .background(Color.Gray)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = { Log.i("TouchTest", "PARENT.. press") },
//                    onTap = { Log.i("TouchTest", "PARENT.. tap") }
//                )
//            }
//    ) {
//        Text(
//            modifier = Modifier
//                .size(100.dp)
//                .background(Color.Blue)
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = { Log.i("TouchTest", "CHILD.. press") },
//                        onTap = { Log.i("TouchTest", "CHILD.. tap") }
//                    )
//                },
//            text = "Click me"
//        )
//    }
//}
//@Composable
//fun TestTouchMain() {
//    val statusBarHeight = with(LocalDensity.current) {
//        WindowInsets.statusBars.getTop(this).toDp()
//    }
//    Box(
//        modifier = Modifier
//            .padding(top = statusBarHeight)
//            .pointerInput(Unit) {
//                detectTapGestures(
//                    onPress = { Log.i("TouchTest", "detectTapGestures.. press") },
//                    onTap = { Log.i("TouchTest", "detectTapGestures.. tap") }
//                )
//            }
//    ) {
//        Text(
//            modifier = Modifier
//                .fillMaxSize()
//                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onPress = { Log.i("TouchTest", "detectTapGestures child.. press") },
//                        onTap = { Log.i("TouchTest", "detectTapGestures child.. tap") }
//                    )
//                }, text = "Test  touch"
//        )
//    }
//}