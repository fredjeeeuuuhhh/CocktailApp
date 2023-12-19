package com.example.cocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.cocktailapp.ui.CocktailAppNavGraph
import com.example.cocktailapp.ui.theme.CocktailAppTheme
import com.example.cocktailapp.util.ContentType
import com.example.cocktailapp.util.NavigationType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =  MaterialTheme.colorScheme.background,
                ) {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    when(windowSize.widthSizeClass){
                        WindowWidthSizeClass.Compact -> {
                            CocktailAppNavGraph()
                        }
                        WindowWidthSizeClass.Medium -> {
                            CocktailAppNavGraph(NavigationType.NAVIGATION_RAIL, ContentType.LIST_ONLY)
                        }
                        WindowWidthSizeClass.Expanded -> {
                            CocktailAppNavGraph(NavigationType.NAVIGATION_DRAWER, ContentType.LIST_AND_DETAIL)
                        }
                        else -> {
                            CocktailAppNavGraph()
                        }
                    }
                }
            }
        }
    }
}


