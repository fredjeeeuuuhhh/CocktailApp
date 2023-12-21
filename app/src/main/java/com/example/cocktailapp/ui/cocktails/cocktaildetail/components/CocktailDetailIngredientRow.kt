package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.cocktailapp.R

@Composable
fun CocktailDetailIngredientRow(
    ingredientName: String,
    measurement: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.padding_extra_small),
                end = dimensionResource(id = R.dimen.padding_small),
            ),
    ) {
        Box() {
            Row {
                AsyncImage(
                    model = stringResource(R.string.ingredient_thumbnail, ingredientName),
                    contentDescription = ingredientName,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.cocktail_detail_icon))
                        .height(dimensionResource(id = R.dimen.cocktail_detail_icon))
                        .aspectRatio(1f),
                )
                Text(
                    text = ingredientName,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
        Text(
            text = measurement,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
