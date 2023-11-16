package com.example.cocktailapp.ui

import androidx.compose.ui.input.key.Key.Companion.F
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.cocktailapp.ui.AdminScreens.COCKTAIL_SCREEN
import com.example.cocktailapp.ui.AdminScreens.FAVORITE_SCREEN
import com.example.cocktailapp.ui.AdminScreens.INGREDIENT_SCREEN

private object AdminScreens {
    const val COCKTAIL_SCREEN = "cocktails"
    const val INGREDIENT_SCREEN = "ingredients"
    const val FAVORITE_SCREEN = "favorites"
}

/**
 * Destinations used in the [AdminApp]
 */
object CocktailDestinations {
    const val COCKTAIL_ROUTE = COCKTAIL_SCREEN
    const val INGREDIENT_ROUTE = INGREDIENT_SCREEN
    const val FAVORITE_ROUTE = FAVORITE_SCREEN
}

/**
 * Models the navigation actions in the app.
 */
class CocktailNavigationActions(private val navController: NavHostController) {
    fun navigateToCocktails() {
        navigateToMenuItem(CocktailDestinations.COCKTAIL_ROUTE)
    }

    fun navigateToIngredients() {
        navigateToMenuItem(CocktailDestinations.INGREDIENT_ROUTE)
    }

    fun navigateToFavorites() {
        navigateToMenuItem(CocktailDestinations.FAVORITE_ROUTE)
    }

    fun navigateToMenuItem(route: String) {
        navController.navigate(route) {
            F
            // Pop up to the start destination of the graph to avoid building up a large stack of destinations
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}