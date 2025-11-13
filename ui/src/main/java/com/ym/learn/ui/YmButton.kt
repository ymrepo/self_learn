package com.ym.learn.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ym.learn.ui.theme.PrimaryColor

enum class YmButtonStyle {
    Filled, Outline, None
}

@Composable
@Preview
fun TestFilled() {
    YmFilledButton(YmButtonStyle.Filled, "Continu", onClick = {})
}

@Composable
fun YmFilledButton(style: YmButtonStyle, text: String, onClick: () -> Unit) {
    when (style) {
        YmButtonStyle.Filled -> {
            Button(
                onClick = { onClick.invoke() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = ButtonColors(
                    containerColor = PrimaryColor,
                    contentColor = Color.Blue,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.Gray
                )

            ) {
                Text(
                    text = text,
                    color = Color.White
                )
            }
        }

        YmButtonStyle.Outline -> {
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
                Text(
                    text = "button",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .fillMaxWidth()
                        .height(48.0.dp)
                )
            }
        }

        YmButtonStyle.None -> {
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
                Text(
                    text = "button",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .fillMaxWidth()
                        .height(48.0.dp)
                )
            }
        }
    }

}