package com.example.helloworld

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.helloworld.ui.theme.HelloWorldTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MyButtonComponentsTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun defaultGuestGreetingTest() {
    composeTestRule.setContent {
      HelloWorldTheme {
        MainScreen()
      }
    }
    composeTestRule.onNodeWithText(CLICK_HERE_BUTTON_TEXT).performClick()
    composeTestRule.onNodeWithText("Hello $DEFAULT_NAME_TEXT!").assertIsDisplayed()

    composeTestRule.onNodeWithText(FINISHED_BUTTON_TEXT).performClick()
    composeTestRule.onNodeWithText("Hello $DEFAULT_NAME_TEXT!").assertDoesNotExist()
  }

  @Test
  fun specificNameGreetingTest() {
    val name = "Buddy"
    composeTestRule.setContent {

      HelloWorldTheme {
        MainScreen(name)
      }
    }

    composeTestRule.onNodeWithText(CLICK_HERE_BUTTON_TEXT).performClick()
    composeTestRule.onNodeWithText("Hello $name!").assertIsDisplayed()

    composeTestRule.onNodeWithText(FINISHED_BUTTON_TEXT).performClick()
    composeTestRule.onNodeWithText("Hello $name!").assertDoesNotExist()
  }
}
