package com.example.cocktailapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.cocktailapp.ui.CocktailAppNavGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class NavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private val activity get() = composeTestRule.activity

    lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject() // Inject dependencies using Hilt
        composeTestRule.setContent {
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            CocktailAppNavGraph(navController = navController)
        }
    }

    @Test
    fun verifyStartDestination() {
        composeTestRule.onNodeWithText(activity.getString(R.string.title_cocktails)).assertIsDisplayed()
        composeTestRule.onNodeWithText(activity.getString(R.string.title_ingredients)).assertIsDisplayed()
    }

    @Test
    fun navigateToIngredients() {
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.title_ingredients)).performClick()
        composeTestRule.onNodeWithText(activity.getString(R.string.title_cocktails)).assertIsDisplayed()
        composeTestRule.onNodeWithText(activity.getString(R.string.title_ingredients)).assertIsDisplayed()
    }
}