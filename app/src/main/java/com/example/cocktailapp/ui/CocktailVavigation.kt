package com.example.cocktailapp.ui

import androidx.compose.ui.input.key.Key.Companion.F
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.cocktailapp.ui.CocktailDestinationsArgs.COCKTAIL_ID_ARG
import com.example.cocktailapp.ui.CocktailDestinationsArgs.INGREDIENT_NAME_ARG
import com.example.cocktailapp.ui.CocktailScreens.COCKTAIL_DETAIL_SCREEN
import com.example.cocktailapp.ui.CocktailScreens.COCKTAIL_SCREEN
import com.example.cocktailapp.ui.CocktailScreens.INGREDIENT_DETAIL_SCREEN
import com.example.cocktailapp.ui.CocktailScreens.INGREDIENT_SCREEN

private object CocktailScreens {
    const val COCKTAIL_SCREEN = "cocktails"
    const val COCKTAIL_DETAIL_SCREEN = "detailCocktail"
    const val INGREDIENT_SCREEN = "ingredients"
    const val INGREDIENT_DETAIL_SCREEN = "detailIngredient"
}
object CocktailDestinationsArgs {

    const val COCKTAIL_ID_ARG = "cocktailId"
    const val INGREDIENT_NAME_ARG = "ingredientName"
}

object CocktailDestinations {
    const val COCKTAIL_ROUTE = COCKTAIL_SCREEN
    const val COCKTAIL_DETAIL_ROUTE = "$COCKTAIL_DETAIL_SCREEN/{$COCKTAIL_ID_ARG}"
    const val INGREDIENT_ROUTE = INGREDIENT_SCREEN
    const val INGREDIENT_DETAIL_ROUTE = "$INGREDIENT_DETAIL_SCREEN/{$INGREDIENT_NAME_ARG}"
}

class CocktailNavigationActions(private val navController: NavHostController) {
    fun navigateToCocktails() {
        navigateToMenuItem(CocktailDestinations.COCKTAIL_ROUTE)
    }

    fun navigateToIngredients() {
        navigateToMenuItem(CocktailDestinations.INGREDIENT_ROUTE)
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

    fun navigateToCocktailDetail(cocktailId: Int) {
        navController.navigate("$COCKTAIL_DETAIL_SCREEN/$cocktailId")
    }
    fun navigateToIngredientDetail(ingredientName: String) {
        navController.navigate("$INGREDIENT_DETAIL_SCREEN/$ingredientName")
    }
}

