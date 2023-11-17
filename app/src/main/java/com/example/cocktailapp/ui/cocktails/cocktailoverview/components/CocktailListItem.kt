package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cocktailapp.model.Cocktail

@Composable
fun CocktailListItem(
    cocktail: Cocktail,
    onViewDetailClicked: (Cocktail) -> Unit,
){
    Card(
        modifier = Modifier.padding(10.dp).clickable { onViewDetailClicked(cocktail) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
    ){
        Column(
            Modifier.padding(5.dp)
        ){
            Text(cocktail.strDrink)
            Text(cocktail.strAlcoholic)
            Text(cocktail.strCategory)
            Text(cocktail.strGlass)
            Text(cocktail.strDrinkThumb)
        }
    }


}