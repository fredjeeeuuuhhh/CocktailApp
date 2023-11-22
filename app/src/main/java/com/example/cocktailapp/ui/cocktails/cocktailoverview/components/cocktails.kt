package com.example.cocktailapp.ui.cocktails.cocktailoverview.components // ktlint-disable filename

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.example.cocktailapp.model.Cocktail

@Composable
fun Cocktails(
    cocktails: List<Cocktail>,
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val lazyGridState = LazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = lazyGridState
    ) {
        items(cocktails) { cocktail ->
            CocktailListItem(
                cocktail = cocktail,
                onViewDetailClicked = { onViewDetailClicked(cocktail) },
            )
        }
    }
}
