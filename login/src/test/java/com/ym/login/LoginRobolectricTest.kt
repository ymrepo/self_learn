package com.ym.login

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.samples.apps.nowinandroid.core.testing.util.captureMultiDevice
import com.ym.learn.login.LoginPhoneContent
import com.ym.learn.ui.theme.Self_learnTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

//@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [Build.VERSION_CODES.VANILLA_ICE_CREAM]) // 添加这行：API 26 = Build.VERSION_CODES.O
class LoginRobolectricTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun basicScreenshotTest() {
        composeTestRule.captureMultiDevice("LoginPhoneContent") {
            Self_learnTheme {
                LoginPhoneContent(
                    isLoading = false,
                    phone = "12345678901",
                    maxValue = 7,
                    loginOnClick = {},
                    clearOnClick = {},
                    onValueChanged = { value -> },
                    modifier = Modifier
                )
            }
        }
    }
}