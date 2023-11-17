package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Cocktail

@Composable
fun Cocktails(
    cocktails: List<Cocktail>,
    onViewDetailClicked: (Cocktail) -> Unit,
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(top = 10.dp),
    ){
        items(cocktails){cocktail->
            CocktailListItem(
                cocktail = cocktail,
                onViewDetailClicked = { onViewDetailClicked(cocktail) }
            )
        }
    }
}