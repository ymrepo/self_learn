package com.ym.learn.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class YmButtonStyle {
    Filled, Outline, None
}

@Preview(showBackground = true)
@Composable
fun YmFilledButton() {
    Button(
        onClick = {},
        modifier = Modifier,
        colors = ButtonColors(
            containerColor = Color.Red,
            contentColor = Color.Blue,
            disabledContentColor = Color.LightGray,
            disabledContainerColor = Color.Gray
        )

    ) {
        Text(text = "button",
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
            .fillMaxWidth()
            .height(48.0.dp))
    }
}