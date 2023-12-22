package com.example.cocktailapp.ui.ingredients.ingredientsoverview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.cocktailapp.R
import com.example.cocktailapp.model.Ingredient

@Composable
fun Ingredients(
    ingredients: List<Ingredient>,
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val lazyGridState = LazyGridState()
    Divider(
        modifier = Modifier,
        thickness = dimensionResource(id = R.dimen.separatot_thickness),
        color = MaterialTheme.colorScheme.outline,
    )
    if (ingredients.isEmpty()) {
        Text(
            text = stringResource(id = R.string.no_ingredients),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small)),
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium)),
        state = lazyGridState,
    ) {
        items(ingredients) { ingredient ->
            IngredientListItem(
                ingredient = ingredient,
                onViewDetailClicked = { onViewDetailClicked(ingredient) },
            )
        }
    }
}
