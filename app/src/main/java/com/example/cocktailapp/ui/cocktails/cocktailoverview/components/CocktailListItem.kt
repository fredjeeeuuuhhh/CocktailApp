package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktailapp.model.Cocktail

@Composable
fun CocktailListItem(
    cocktail: Cocktail,
    onViewDetailClicked: (Cocktail) -> Unit,
){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable { onViewDetailClicked(cocktail) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White,
        ),
    ) {
        AsyncImage(
            model = cocktail.strDrinkThumb+"/preview",
            contentDescription = cocktail.strDrink,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text= cocktail.strDrink,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(10.dp),
            textAlign = TextAlign.Center,
        )
    }

}







    //Card(
        //modifier = Modifier
       //     .padding(start=10.dp)
      //      .background(Color.DarkGray)
      //      .clickable { onViewDetailClicked(cocktail) },
      //  elevation = CardDefaults.cardElevation(
       //     defaultElevation = 12.dp
      //  ),
