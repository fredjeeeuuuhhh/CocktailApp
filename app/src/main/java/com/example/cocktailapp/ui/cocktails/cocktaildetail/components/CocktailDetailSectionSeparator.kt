package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.cocktailapp.R

@Composable
fun CocktailDetailSectionSeparartor() {
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
    Divider(color = MaterialTheme.colorScheme.outline, thickness = dimensionResource(id = R.dimen.separatot_thickness))
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
}
