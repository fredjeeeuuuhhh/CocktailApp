package com.example.cocktailapp.ui.cocktails.cocktaildetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
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

@Composable
fun CocktaiilIngredientHeader(
    onBack: ()->Unit,
    title:String,
    isFavorite:Boolean,
    onStarClicked: (Boolean)->Unit,
){
    var selected by remember { mutableStateOf(isFavorite) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { onBack() },
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Close dialog",
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
                selected=!selected
                onStarClicked(!isFavorite)
                      },
        ) {
            if(selected){
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Close dialog",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }else{
                Icon(
                    Icons.Filled.StarOutline,
                    contentDescription = "Close dialog",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

        }
    }
}