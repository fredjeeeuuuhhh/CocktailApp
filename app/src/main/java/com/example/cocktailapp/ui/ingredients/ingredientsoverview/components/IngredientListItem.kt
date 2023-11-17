package com.example.cocktailapp.ui.ingredients.ingredientsoverview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Ingredient

@Composable
fun IngredientListItem(
    ingredient: Ingredient,
    onViewDetailClicked: (Ingredient) -> Unit,
){
    Card(
        modifier = Modifier.padding(10.dp).clickable { onViewDetailClicked(ingredient) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
    ){
        Column(
            Modifier.padding(5.dp)
        ){
            Text(ingredient.strIngredient)
            Text(ingredient.strIngredientThumb)
        }
    }
}