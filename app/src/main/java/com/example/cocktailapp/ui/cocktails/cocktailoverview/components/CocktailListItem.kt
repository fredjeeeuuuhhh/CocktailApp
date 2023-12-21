package com.example.cocktailapp.ui.cocktails.cocktailoverview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.cocktailapp.R
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.components.AutoResizedText

@Composable
fun CocktailListItem(
    cocktail: Cocktail,
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .testTag(tag = stringResource(R.string.cocktail_tag))
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inverseSurface)
                    .padding(dimensionResource(id = R.dimen.padding_extra_small)),
            ) {
                if (cocktail.ingredientNames != null) {
                    if (cocktail.ingredientNames!!.size == 0 || cocktail.ingredientNames!!.size != cocktail.nrOwnedIngredients) {
                        Text(text = "${cocktail.nrOwnedIngredients}/${cocktail.ingredientNames!!.size}")
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = stringResource(R.string.icon_locked))
                    } else {
                        Text(text = "${cocktail.nrOwnedIngredients}/${cocktail.ingredientNames!!.size}")
                        Icon(imageVector = Icons.Filled.LockOpen, contentDescription = stringResource(R.string.icon_unlocked))
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalArrangement = Arrangement.Center,
        ) {
            AutoResizedText(
                text = cocktail.title,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
