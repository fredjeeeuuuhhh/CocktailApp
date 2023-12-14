package com.example.cocktailapp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cocktailapp.ui.CocktailAppNavGraph
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CocktailAppNavGraph(navController = navController)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule
            .onNodeWithText("Cocktails")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Ingredients")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToIngredients() {
        composeTestRule
            .onNodeWithText("Ingredients")
            .performClick()
        composeTestRule
            .onNodeWithText("Cocktails")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Ingredients")
            .assertIsDisplayed()
    }

    @Test
    fun navigateToFirstCocktailCard() {
        composeTestRule
            .onAllNodesWithTag("CocktailCard")[0].performClick()
        composeTestRule
            .onNodeWithText("Instructions")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Ingredients")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Cocktails")
            .assertDoesNotExist()
    }

    @Test
    fun navigateToFirstIngredientCard() {
        composeTestRule
            .onNodeWithText("Ingredients")
            .performClick()
        composeTestRule
            .onAllNodesWithTag("IngredientCard")[0].performClick()
        composeTestRule
        composeTestRule
            .onNodeWithText("Ingredients")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Cocktails")
            .assertDoesNotExist()

    }




}