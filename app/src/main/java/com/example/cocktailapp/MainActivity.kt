package com.example.cocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktailapp.data.BottomNavItem
import com.example.cocktailapp.layout.BottomBar
import com.example.cocktailapp.layout.CocktailOverview
import com.example.cocktailapp.layout.FavoritesOverview
import com.example.cocktailapp.layout.IngredientsOverview
import com.example.cocktailapp.ui.theme.CocktailAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailAppTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                    Scaffold(
                        topBar = {},
                        bottomBar = {
                            BottomBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Cocktails",
                                        route = "cocktails",
                                        icon = Icons.Filled.LocalBar,
                                    ),
                                    BottomNavItem(
                                        name = "Ingredients",
                                        route = "ingredients",
                                        icon = Icons.Filled.Liquor,
                                    ),
                                    BottomNavItem(
                                        name = "Favorites",
                                        route = "favorites",
                                        icon = Icons.Filled.Favorite
                                    )
                                ),
                                navController = navController,
                                modifier = Modifier,
                                onItemClick = {
                                    navController.navigate(it.route)
                                },
                            )
                        },
                        floatingActionButton = {},
                    ) { innerPadding ->
                        innerPadding
                        Navigation(navController = navController)

                    }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(navController = navController, startDestination = "cocktails") {
        composable("cocktails") {
            CocktailOverview()
        }
        composable("ingredients") {
            IngredientsOverview()
        }
        composable("favorites") {
            FavoritesOverview()
        }
    }
}
