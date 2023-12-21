package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.IngredientApiState
import com.example.cocktailapp.ui.components.FilterChipsView
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.components.Ingredients
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientsScreen(
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
        when (ingredientApiState) {
            is IngredientApiState.Loading -> {
                Text(text = stringResource(id = R.string.loading_ingredient_overview))
            }
            is IngredientApiState.Error -> {
                Text(text = stringResource(id = R.string.error_ingredient_overview))
            }
            is IngredientApiState.Succes -> {
                IngredientsOverview(
                    ingredients = ingredientApiState.ingredients,
                    onViewDetailClicked = onViewDetailClicked,
                )
            }
        }
        PullRefreshIndicator(
            refreshing = ingredientOverviewState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
        )
    }
}

@Composable
fun IngredientsOverview(
    ingredients: List<Ingredient>,
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val filters = mutableMapOf<String, (Ingredient) -> Boolean>(
        stringResource(R.string.filter_all) to { true },
    )

    for (char in stringResource(id = R.string.alphabet).split(","))
        filters += char to { it.name.first().lowercaseChar() == char.first() }

    var currentFilter by rememberSaveable { mutableStateOf(filters.toList()[0].first) }

    FilterChipsView(filters, currentFilter, { currentFilter = it }) {
        Ingredients(
            ingredients = ingredients.filter(filters[currentFilter]!!),
            onViewDetailClicked = onViewDetailClicked,
        )
    }
}
