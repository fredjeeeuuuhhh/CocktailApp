package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.components.Ingredients

@Composable
fun IngredientsOverview(
    ingredientOverviewViewModel: IngredientsOverviewViewModel = IngredientsOverviewViewModel(),
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val ingredientOverviewState by ingredientOverviewViewModel.uiState.collectAsState()
    Ingredients(
        ingredients = ingredientOverviewState.currentIngredientList!!,
        onViewDetailClicked = onViewDetailClicked,
    )
}
