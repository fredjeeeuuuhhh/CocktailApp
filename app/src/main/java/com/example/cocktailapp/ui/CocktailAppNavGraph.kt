package com.example.cocktailapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocktailapp.R
import com.example.cocktailapp.ui.CocktailDestinationsArgs.COCKTAIL_NAME_ARG
import com.example.cocktailapp.ui.CocktailDestinationsArgs.INGREDIENT_NAME_ARG
import com.example.cocktailapp.ui.cocktails.cocktaildetail.CocktailDetail
import com.example.cocktailapp.ui.cocktails.cocktaildetail.CocktailDetailViewModel
import com.example.cocktailapp.ui.cocktails.cocktailoverview.CocktailOverview
import com.example.cocktailapp.ui.favorites.RandomSelect
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.IngredientDetail
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.IngredientDetailViewModel
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.IngredientsOverview
import com.example.cocktailapp.ui.navigation.BottomNavigationBar
import com.example.cocktailapp.ui.navigation.NavigationMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailAppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = CocktailDestinations.COCKTAIL_ROUTE,
    navActions: CocktailNavigationActions = remember(navController) {
        CocktailNavigationActions(navController)
    },
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    val cocktailsMenuItem = NavigationMenuItem(
        route = CocktailDestinations.COCKTAIL_ROUTE,
        title = R.string.title_cocktails,
        icon = Icons.Filled.LocalBar,
        navigationAction = { navActions.navigateToCocktails() },
    )

    val ingredientsMenuItem = NavigationMenuItem(
        route = CocktailDestinations.INGREDIENT_ROUTE,
        title = R.string.title_ingredients,
        icon = Icons.Filled.Liquor,
        navigationAction = {
            navActions.navigateToIngredients()
        },
    )
    val favoritesMenuItem = NavigationMenuItem(
        route = CocktailDestinations.RANDOMSELECT_ROUTE,
        title = R.string.title_random,
        icon = Icons.Filled.QuestionMark,
        navigationAction = {
            navActions.navigateToFavorites()
        },
    )

    val menuItems = arrayOf(cocktailsMenuItem, ingredientsMenuItem, favoritesMenuItem)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentRoute, menuItems)
        },
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = startDestination,
            Modifier.padding(innerPadding),
        ) {
            composable(CocktailDestinations.COCKTAIL_ROUTE) {
                CocktailOverview(
                    onViewDetailClicked = { cocktail -> navActions.navigateToCocktailDetail(cocktail.strDrink) },
                )
            }
            composable(CocktailDestinations.INGREDIENT_ROUTE) {
                IngredientsOverview(
                    onViewDetailClicked = { ingredient -> navActions.navigateToIngredientDetail(ingredient.strIngredient) },
                )
            }
            composable(CocktailDestinations.RANDOMSELECT_ROUTE) {
               RandomSelect()
            }

            composable(CocktailDestinations.COCKTAIL_DETAIL_ROUTE) { entry ->
                CocktailDetail(
                    onBack = { navController.popBackStack() },
                )
            }

            composable(CocktailDestinations.INGREDIENT_DETAIL_ROUTE) { entry ->
                val ingredientName = entry.arguments?.getString(INGREDIENT_NAME_ARG)
                val parentEntry = remember(entry) { navController.getBackStackEntry(CocktailDestinations.INGREDIENT_DETAIL_ROUTE) }
                val viewModel = IngredientDetailViewModel()
                IngredientDetail(
                    onBack = { navController.popBackStack() },
                    ingredientDetailViewModel = viewModel,
                    name = ingredientName,
                )
            }
        }
    }
}
