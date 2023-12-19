package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.cocktails.components.AutoResizedText

@Composable
fun CocktailListItem(
    cocktail: Cocktail,
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .testTag("CocktailCard")
            .clickable { onViewDetailClicked(cocktail) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        AsyncImage(
            model = cocktail.image,
            contentDescription = cocktail.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )

        AutoResizedText(
            text =  cocktail.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(30.dp).fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}


