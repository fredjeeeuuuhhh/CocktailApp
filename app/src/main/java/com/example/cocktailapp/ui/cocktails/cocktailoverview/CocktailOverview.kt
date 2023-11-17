package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.cocktails.cocktailoverview.components.Cocktails

@Composable
fun CocktailOverview(
    cocktailOverviewViewModel: CocktailOverviewViewModel = CocktailOverviewViewModel(),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val cocktailOverviewState by cocktailOverviewViewModel.uiState.collectAsState()

    Cocktails(
        cocktails = cocktailOverviewState.currentCocktailList!!,
        onViewDetailClicked = onViewDetailClicked,
    )
}
