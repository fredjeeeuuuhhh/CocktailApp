package com.example.cocktailapp.ui.ingredients.ingredientsoverview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Ingredient

@Composable
fun Ingredients(
    ingredients: List<Ingredient>,
    onViewDetailClicked: (Ingredient) -> Unit,
) {
    val lazyGridState = LazyGridState()
    Divider(
        Modifier,
        2.dp,
        MaterialTheme.colorScheme.outline,
    )
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(top = 10.dp),
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
