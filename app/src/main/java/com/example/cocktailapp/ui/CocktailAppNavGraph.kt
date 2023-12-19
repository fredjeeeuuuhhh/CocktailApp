package com.example.cocktailapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocktailapp.R
import com.example.cocktailapp.ui.cocktails.cocktaildetail.CocktailDetail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.CocktailScreen
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.IngredientDetail
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.IngredientsOverview
import com.example.cocktailapp.ui.navigation.BottomNavigationBar
import com.example.cocktailapp.ui.navigation.NavigationDrawerContent
import com.example.cocktailapp.ui.navigation.NavigationMenuItem
import com.example.cocktailapp.ui.navigation.TaskNavigationRail
import com.example.cocktailapp.util.ContentType
import com.example.cocktailapp.util.NavigationType

@Composable
fun CocktailAppNavGraph(
    navigationType: NavigationType = NavigationType.BOTTOM_BAR,
    contentType: ContentType = ContentType.LIST_ONLY,
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
                currentRoute = currentRoute,
                menuItems,
                navigationType = navigationType,
            ) {
                CocktailScreen(
                    onViewDetailClicked = { cocktail -> navActions.navigateToCocktailDetail(cocktail.id) },
                )
            }
        }
        composable(CocktailDestinations.INGREDIENT_ROUTE) {
            MenuScaffold(
                currentRoute = currentRoute,
                menuItems,
                navigationType = navigationType,
            ) {
                IngredientsOverview(
                    onViewDetailClicked = { ingredient ->
                        navActions.navigateToIngredientDetail(
                            ingredient.name,
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
                onViewDetailClicked = { cocktail -> navActions.navigateToCocktailDetail(cocktail.id) },
            )
        }
    }
}

@Composable
fun MenuScaffold(currentRoute: String, menuItems: Array<NavigationMenuItem>, navigationType: NavigationType, content: @Composable () -> Unit) {
    if (navigationType == NavigationType.NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentDrawerSheet(Modifier.width(dimensionResource(R.dimen.drawer_width))) {
                NavigationDrawerContent(
                    currentRoute,
                    menuItems,
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(dimensionResource(R.dimen.drawer_padding_content)),
                )
            }
        }) {
            Scaffold { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    content()
                }
            }
        }
    } else if (navigationType == NavigationType.BOTTOM_BAR) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(currentRoute, menuItems)
            },
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                content()
            }
        }
    } else {
        Row {
            AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                TaskNavigationRail(
                    currentRoute = currentRoute,
                    menuItems,
                )
            }
            Scaffold { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    content()
                }
            }
        }
    }
}
