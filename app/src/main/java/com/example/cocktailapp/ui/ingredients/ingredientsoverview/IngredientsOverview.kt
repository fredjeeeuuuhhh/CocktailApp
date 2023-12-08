package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiState
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.components.Ingredients


@Composable
fun IngredientsOverview(
    ingredientOverviewViewModel: IngredientsOverviewViewModel = hiltViewModel(),
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val uiListState by ingredientOverviewViewModel.uiListState.collectAsState()
    val ingredientApiState = ingredientOverviewViewModel.ingredientApiState
    when(ingredientApiState){
        is IngredientApiState.Loading-> {
            Text("Loading ingredients from api")
        }
        is IngredientApiState.Error-> {
            Text("Error while retrieving ingredients from api")
        }
        is IngredientApiState.Success -> {
            Ingredients(
                ingredients = uiListState,
                onViewDetailClicked = onViewDetailClicked,
            )
        }
    }
}
