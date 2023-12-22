package com.example.cocktailapp.ui.cocktails.cocktailoverview.components // ktlint-disable filename

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
import com.example.cocktailapp.model.Cocktail

@Composable
fun Cocktails(
    cocktails: List<Cocktail>,
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val lazyGridState = LazyGridState()
    Divider(
        modifier = Modifier,
        thickness = dimensionResource(id = R.dimen.separatot_thickness),
        color = MaterialTheme.colorScheme.outline,
    )
    if (cocktails.isEmpty()) {
        Text(
            text = stringResource(id = R.string.no_cocktails),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_small)),
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        state = lazyGridState,
    ) {
        items(cocktails) { cocktail ->
            CocktailListItem(
                cocktail = cocktail,
                onViewDetailClicked = { onViewDetailClicked(cocktail) },
            )
        }
    }
}
