package com.ym.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ym.learn.login.LoginPhoneScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyComposeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testComposeUI() {
        composeTestRule.setContent { LoginPhoneScreen(rememberNavController()) }
//        composeTestRule.onNodeWithTag("SubmitButton").assertDoesNotExist()
        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }
}