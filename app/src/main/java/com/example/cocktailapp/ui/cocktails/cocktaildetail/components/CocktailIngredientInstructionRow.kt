package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.cocktailapp.R

@Composable
fun CocktailIngredientInstructionRow(
    i: Int,
    instruction: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_extra_small)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = (i + 1).toString(),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = instruction,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
