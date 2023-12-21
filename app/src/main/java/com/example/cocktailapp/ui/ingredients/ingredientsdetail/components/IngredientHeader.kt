package com.example.cocktailapp.ui.ingredients.ingredientsdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.cocktailapp.R

@Composable
fun IngredientHeader(
    onBack: () -> Unit,
    title: String,
    isOwned: Boolean,
    onIsOwnedChanged: (Boolean) -> Unit,
) {
    var selected by remember { mutableStateOf(isOwned) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {
                onBack()
            },
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.arrow_back),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
        )

        IconButton(
            onClick = {
                selected = !selected
                onIsOwnedChanged(!isOwned)
            },
        ) {
            if (selected) {
                Icon(
                    Icons.Filled.RemoveCircleOutline,
                    contentDescription = stringResource(id = R.string.ingredient_not_owned),
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Icon(
                    Icons.Filled.AddCircleOutline,
                    contentDescription = stringResource(id = R.string.ingredient_owned),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
