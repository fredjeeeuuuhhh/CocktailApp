package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.cocktailapp.R

@Composable
fun CocktailDetailSubTitle(
    @StringRes label: Int,
    align: TextAlign = TextAlign.Start,
    color: Color,
) {
    Text(
        text = stringResource(id = label),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_extra_small)),
        color = color,
        style = MaterialTheme.typography.titleLarge,
        textAlign = align,
    )
}
