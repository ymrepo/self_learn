package com.ym.login

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GreetingTest {

    // 创建 Compose 测试规则
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun greetingDisplaysCorrectly() {
        // 1. 设置测试内容：将 Greeting 组件放入测试环境中
        composeTestRule.setContent {
            Greeting("World")
        }

        // 2. 执行交互：查找包含特定文本的节点并点击（如果需要）
        // composeTestRule.onNodeWithText("Submit").performClick()

        // 3. 断言结果：验证 UI 是否按预期显示
        composeTestRule.onNodeWithText("Hello, World!").assertExists()
        // 或者更严格的断言，检查是否显示
        // composeTestRule.onNodeWithText("Hello, World!").assertIsDisplayed()
    }
}
