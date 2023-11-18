package com.example.cocktailapp.ui.ingredients.ingredientsoverview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktailapp.model.Ingredient

@Composable
fun IngredientListItem(
    ingredient: Ingredient,
    onViewDetailClicked: () -> Unit,
    onOwnedStatusChanged : () -> Unit
){
    var isOwned by rememberSaveable{ mutableStateOf(ingredient.isOwned)}
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable { onViewDetailClicked() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray,
            contentColor = Color.White,
        ),
    ) {
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .height(35.dp)
                .padding(top = 10.dp),
            contentAlignment = Alignment.CenterEnd,
        ){
            if(!isOwned){
                IconButton(onClick = {
                    isOwned=true
                    onOwnedStatusChanged()
                }) {
                    Icon(
                        Icons.Filled.AddCircleOutline,
                        "Add to owned",
                        modifier = Modifier.size(35.dp),
                    )
                }
            }else{
                IconButton(onClick = {
                    isOwned=false
                    onOwnedStatusChanged()
                }) {
                    Icon(
                        Icons.Filled.RemoveCircleOutline,
                        "Add to owned",
                        modifier = Modifier.size(35.dp),
                    )
                }
            }

        }
        AsyncImage(
            model = "https://"+ingredient.strIngredientThumb,
            contentDescription = ingredient.strIngredient,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Text(
            text= ingredient.strIngredient,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(5.dp),
            textAlign = TextAlign.Center,
        )
    }
}