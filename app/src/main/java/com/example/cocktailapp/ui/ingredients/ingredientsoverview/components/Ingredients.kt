package com.example.cocktailapp.ui.ingredients.ingredientsoverview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.ingredients.ingredientsoverview.components.IngredientListItem

@Composable
fun Ingredients(
    ingredients: List<Ingredient>,
    onViewDetailClicked: (Ingredient) -> Unit,
    onOwnedStatusChanged: (Ingredient) -> Unit,
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(top = 10.dp),
    ){
        items(ingredients){ingredient->
            IngredientListItem(
                ingredient = ingredient,
                onViewDetailClicked = { onViewDetailClicked(ingredient) },
                onOwnedStatusChanged = { onOwnedStatusChanged(ingredient) }
            )
        }
    }
}