package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CocktailDetailSectionSeparartor(){
    Spacer(modifier = Modifier.padding(6.dp))
    Divider(color = MaterialTheme.colorScheme.outline, thickness = 3.dp)
    Spacer(modifier = Modifier.padding(6.dp))
}