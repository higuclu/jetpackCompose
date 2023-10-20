package com.pazarama.bootcamp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyFieldsSnackbar() {
        composeTestRule.setContent {
            GUI()
        }

        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.onNodeWithText("Username and password can not be empty").assertExists()
    }

    @Test
    fun testIncorrectCredentialsSnackbar() {
        composeTestRule.setContent {
            GUI()
        }

        composeTestRule.onNodeWithTag("usernameField").performTextInput("user123")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("wrongPassword")

        composeTestRule.onNodeWithTag("loginButton").performClick()

        composeTestRule.onNodeWithText("Login Failed").assertExists()
    }

    @Test
    fun testCorrectCredentialsSnackbar() {
        composeTestRule.setContent {
            GUI()
        }

        composeTestRule.onNodeWithTag("usernameField").performTextInput("admin")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("123")

        composeTestRule.onNodeWithTag("loginButton").performClick()
    }
}
