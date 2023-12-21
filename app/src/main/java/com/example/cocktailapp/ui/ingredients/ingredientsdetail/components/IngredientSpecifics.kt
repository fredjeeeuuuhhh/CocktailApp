package com.example.cocktailapp.ui.ingredients.ingredientsdetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.cocktailapp.R

@Composable
fun IngredientSpecifics(label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)),
    ) {
        Icon(
            Icons.Filled.Circle,
            contentDescription = stringResource(id = R.string.bullet),
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(dimensionResource(id = R.dimen.padding_extra_large)),
        )
        Text(
            text = label,
            modifier = Modifier.fillMaxWidth().padding(dimensionResource(id = R.dimen.padding_extra_small)),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
