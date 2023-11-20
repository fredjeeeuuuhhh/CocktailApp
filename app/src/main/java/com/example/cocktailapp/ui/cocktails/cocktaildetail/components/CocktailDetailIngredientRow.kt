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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CocktailDetailIngredientRow(
    ingredientName:String,
    measurement:String,
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 10.dp),
    ) {
        Box() {
            Row {
                AsyncImage(
                    model = "https://www.thecocktaildb.com/images/ingredients/$ingredientName-Small.png",
                    contentDescription = ingredientName,
                    modifier = Modifier
                        .width(35.dp)
                        .height(35.dp)
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