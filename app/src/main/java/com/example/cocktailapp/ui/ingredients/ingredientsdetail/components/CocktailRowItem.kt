package com.example.cocktailapp.ui.ingredients.ingredientsdetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImage
import com.example.cocktailapp.R
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.components.AutoResizedText

@Composable
fun CocktailRowItem(
    cocktail: Cocktail,
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .width(dimensionResource(id = R.dimen.cocktail_card_ingredient_width))
            .clickable { onViewDetailClicked(cocktail) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_large)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        AsyncImage(
            model = cocktail.image,
            contentDescription = cocktail.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1f),
        )

        AutoResizedText(
            text = cocktail.title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)).fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}
