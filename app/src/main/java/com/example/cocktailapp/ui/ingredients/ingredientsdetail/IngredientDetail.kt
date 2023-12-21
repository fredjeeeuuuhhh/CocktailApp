package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.IngredientDetailApiState
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSectionSeparartor
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.components.CocktailRowItem
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.components.IngredientHeader
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.components.IngredientSpecifics

@Composable
fun IngredientDetail(
    onBack: () -> Unit,
    ingredientDetailViewModel: IngredientDetailViewModel = viewModel(factory = IngredientDetailViewModel.Factory),
    onViewDetailClicked: (Cocktail) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState),
    ) {
        val ingredientDetailApiState: IngredientDetailApiState = ingredientDetailViewModel.ingredientDetailApiState
        when (ingredientDetailApiState) {
            is IngredientDetailApiState.Loading -> {
                Text(text = stringResource(id = R.string.loading_details_ingredient))
            }
            is IngredientDetailApiState.Error -> {
                Text(text = stringResource(id = R.string.error_details_ingredient))
            }
            is IngredientDetailApiState.Succes -> {
                if (ingredientDetailApiState.ingredient.isOwned == null)ingredientDetailApiState.ingredient.isOwned = false
                ingredientDetailApiState.ingredient.isOwned?.let { isOwned ->
                    IngredientHeader(
                        onBack = onBack,
                        title = ingredientDetailApiState.ingredient.name,
                        isOwned = isOwned,
                        onIsOwnedChanged = { flag -> ingredientDetailViewModel.onOwnedChanged(flag) },
                    )
                }
                ingredientDetailApiState.ingredient.containsAlcohol?.let { containsAlcohol ->
                    IngredientSpecifics(label = stringResource(id = R.string.contains_alcohol, if (containsAlcohol) "Yes" else "No"))
                }
                ingredientDetailApiState.ingredient.type?.let { type ->
                    IngredientSpecifics(label = stringResource(id = R.string.type, type))
                }
                ingredientDetailApiState.ingredient.alcoholPercentage?.let { percentage ->
                    IngredientSpecifics(label = stringResource(id = R.string.percentage, percentage))
                }

                CocktailDetailSectionSeparartor()

                ingredientDetailApiState.ingredient.description?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                    CocktailDetailSectionSeparartor()
                }

                LazyRow {
                    items(ingredientDetailApiState.cocktailsContainingIngredient) { cocktail ->
                        CocktailRowItem(
                            cocktail = cocktail,
                            onViewDetailClicked = { onViewDetailClicked(cocktail) },
                        )
                    }
                }
            }
        }
    }
}
