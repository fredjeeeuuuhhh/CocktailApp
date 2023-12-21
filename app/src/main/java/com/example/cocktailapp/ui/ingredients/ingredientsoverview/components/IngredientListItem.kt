package com.example.cocktailapp.ui.ingredients.ingredientsoverview.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.components.AutoResizedText

@Composable
fun IngredientListItem(
    ingredient: Ingredient,
    onViewDetailClicked: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .testTag("IngredientCard")
            .clickable { onViewDetailClicked() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    ) {
        AsyncImage(
            model = ingredient.thumbnail,
            contentDescription = ingredient.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(top = 4.dp),
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            AutoResizedText(text = ingredient.name)
        }
    }
}
