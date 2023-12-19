package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.IngredientApiState
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.components.Ingredients
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsOverview(
    ingredientOverviewViewModel: IngredientsOverviewViewModel = viewModel(factory = IngredientsOverviewViewModel.Factory),
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val ingredientOverviewState by ingredientOverviewViewModel.uiState.collectAsState()
    val ingredientApiState = ingredientOverviewViewModel.ingredientApiState
    val pullRefreshState = rememberPullRefreshState(ingredientOverviewState.isRefreshing, onRefresh = ingredientOverviewViewModel::refreshIngredients)
    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxWidth(),
    ) {
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
}
