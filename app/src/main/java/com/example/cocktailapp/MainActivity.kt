package com.example.cocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.cocktailapp.ui.CocktailAppNavGraph
import com.example.cocktailapp.ui.theme.CocktailAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =  MaterialTheme.colorScheme.background,
                ) {
                    CocktailAppNavGraph()
                }
            }
        }
    }
}


