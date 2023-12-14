package com.example.cocktailapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.example.cocktailapp.ui.cocktails.cocktaildetail.CocktailDetail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.CocktailOverview
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.IngredientDetail
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.IngredientsOverview
import com.example.cocktailapp.ui.navigation.BottomNavigationBar
import com.example.cocktailapp.ui.navigation.NavigationMenuItem

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

    val menuItems = arrayOf(cocktailsMenuItem, ingredientsMenuItem)

    NavHost(
        navController,
        startDestination = startDestination,
    ) {
        composable(CocktailDestinations.COCKTAIL_ROUTE) {
            MenuScaffold(
                currentRoute=currentRoute,menuItems
            ) {
                CocktailOverview(
                    onViewDetailClicked = { cocktail -> navActions.navigateToCocktailDetail(cocktail.id) },
                )
            }
        }
        composable(CocktailDestinations.INGREDIENT_ROUTE) {
            MenuScaffold(
                currentRoute=currentRoute,menuItems
            ) {
                IngredientsOverview(
                    onViewDetailClicked = { ingredient ->
                        navActions.navigateToIngredientDetail(
                            ingredient.name
                        )
                    },
                )
            }
        }


        composable(CocktailDestinations.COCKTAIL_DETAIL_ROUTE) {
            CocktailDetail(
                onBack = { navController.popBackStack() },
            )
        }

        composable(CocktailDestinations.INGREDIENT_DETAIL_ROUTE) {
            IngredientDetail(
                onBack = { navController.popBackStack() },
                onViewDetailClicked = {cocktail -> navActions.navigateToCocktailDetail(cocktail.id)}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScaffold(currentRoute: String, menuItems: Array<NavigationMenuItem>, content: @Composable () -> Unit) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentRoute, menuItems)
        },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            content()
        }
    }
}

