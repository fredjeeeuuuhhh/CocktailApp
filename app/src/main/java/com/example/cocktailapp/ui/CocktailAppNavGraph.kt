package com.example.cocktailapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalBar
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
import com.example.cocktailapp.ui.layout.CocktailOverview
import com.example.cocktailapp.ui.layout.FavoritesOverview
import com.example.cocktailapp.ui.layout.IngredientsOverview
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
        route = CocktailDestinations.FAVORITE_ROUTE,
        title = R.string.title_favorites,
        icon = Icons.Filled.Favorite,
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
                CocktailOverview()
            }
            composable(CocktailDestinations.INGREDIENT_ROUTE) {
                IngredientsOverview()
            }
            composable(CocktailDestinations.FAVORITE_ROUTE) {
                FavoritesOverview()
            }
        }
    }
}