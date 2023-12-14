package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.IngredientApiState
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.components.Ingredients


@Composable
fun IngredientsOverview(
    ingredientOverviewViewModel: IngredientsOverviewViewModel = viewModel(factory = IngredientsOverviewViewModel.Factory),
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val ingredientOverviewState by ingredientOverviewViewModel.uiState.collectAsState()
    val ingredientApiState = ingredientOverviewViewModel.ingredientApiState
    when(ingredientApiState){
        is IngredientApiState.Loading-> {
            Text("Loading ingredients from api")
        }
        is IngredientApiState.Error-> {
            Text("Error while retrieving ingredients from api")
        }
        is IngredientApiState.Succes -> {
            Ingredients(
                ingredients = ingredientApiState.ingredients,
                onViewDetailClicked = onViewDetailClicked,
                onOwnedStatusChanged = {ingredient -> ingredientOverviewViewModel.changeOwnedStatus(ingredient)},
            )
        }
    }
}
