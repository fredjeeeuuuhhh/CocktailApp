package com.example.cocktailapp.ui.cocktails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cocktailapp.model.Cocktail

@Composable
fun CocktailHeader(onBack: ()-> Unit,title:String){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
    ) { // header
        IconButton(
            onClick = {
                onBack()
            },
            modifier = Modifier
                .padding(end = 16.dp)
                .height(24.dp)
                .width(24.dp),
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Close dialog",
                Modifier
                    .height(24.dp)
                    .width(24.dp),
            )
        }
        Text(
            text = title,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
        )
    }
}