package com.ym.learn.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun YmBoxTextField() {
    var code by remember { mutableStateOf("") }
    var showOriginalText by remember { mutableStateOf(false) }
    val codeLength = 4
    val focusRequesters = remember { List(codeLength) { FocusRequester() } }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // 创建完全透明的文本选择颜色
    val transparentTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent, // 隐藏选择手柄
        backgroundColor = Color.Transparent // 隐藏选择背景
    )
    LaunchedEffect(code) {//当composable方法执行时，launchedEffect根据key 也会启动
        if (code.isNotEmpty()) {
            showOriginalText = true
            delay(200)
            showOriginalText = false
        }
    }

    Row (Modifier.padding()){

        repeat(codeLength) { index ->
            val isFocused = remember(index) { derivedStateOf { code.length == index } }
            val hasValue = index < code.length


            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(
                        width = 2.dp,
                        color = if (isFocused.value) Color.Green else Color.Gray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .clickable {
                        focusRequesters[index].requestFocus()
                        keyboardController?.show()
                    },
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(LocalTextSelectionColors provides transparentTextSelectionColors) {

                    BasicTextField(
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        ),
                        value = if (index == code.length) {
                            TextFieldValue(
                                text = if (hasValue) code[index].toString() else "",
                                selection = TextRange(1)
                            )
                        } else if (index < code.length) {
                            TextFieldValue(
                                selection = TextRange(1),
                                text = if (showOriginalText && code.length - 1 == index) {
                                    code[index].toString()
                                } else {
                                    "•"
                                },
                            )
                        } else {
                            TextFieldValue(
                                selection = TextRange(1),
                                text = "",
                            )
                        },
                        onValueChange = { newValue ->
                            Log.i("YmBoxFiled", "newValue:$newValue code:$code")
                            if (newValue.text.isNotEmpty() && newValue.text.all { it.isDigit() }) {
                                if (code.length <= index) {
                                    code += newValue.text.take(1)
                                    if (code.length < codeLength) {
                                        focusRequesters[code.length].requestFocus()
                                    } else {
                                        focusManager.clearFocus()
                                    }
                                }
                            } else if (newValue.text.isEmpty() && code.length > index) {
                                code = code.substring(0, index)
                                showOriginalText = false
                                if (index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .focusable()
                            .focusRequester(focusRequesters[index])
                            .pointerInput(Unit) {
                                // 完全拦截所有指针事件，防止出现光标位置指示器
                                awaitPointerEventScope {
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        // 不处理任何指针事件
                                    }
                                }
                            },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        cursorBrush = SolidColor(Color.Transparent),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.size(60.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                innerTextField()
                                // 聚焦时显示自定义竖线光标
                                if (isFocused.value) {
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(24.dp)
                                            .background(Color.Green)
                                    )
                                }

                                // 没有值且不聚焦时显示占位符
                                if (!hasValue && !isFocused.value) {
                                    Text(
                                        text = "",
                                        color = Color.LightGray,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    )
                }
            }

            if (index < codeLength - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}


