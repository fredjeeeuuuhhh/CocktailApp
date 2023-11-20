package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CocktailDetailSubTitle(
    label: String,
    align: TextAlign = TextAlign.Start,
    color: Color,
){
    Text(
        text = label,
        modifier = Modifier.fillMaxWidth().padding(5.dp),
        color = color,
        style = MaterialTheme.typography.titleLarge,
        textAlign = align,
    )
}