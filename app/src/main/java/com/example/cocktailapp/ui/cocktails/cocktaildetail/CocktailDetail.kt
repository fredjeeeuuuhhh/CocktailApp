package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.ui.CocktailDetailApiState
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailIngredientRow
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSectionSeparartor
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSubTitle
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientHeader
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientInstructionRow
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientSpecifics

@Composable
fun CocktailDetail(
    onBack: () -> Unit,
    cocktailDetailViewModel: CocktailDetailViewModel = viewModel(factory = CocktailDetailViewModel.Factory),
) {
    val scrollState = rememberScrollState()

    val cocktailDetailApiState = cocktailDetailViewModel.cocktailDetailApiState
    when (cocktailDetailApiState) {
        is CocktailDetailApiState.Loading -> {
            Text(text = stringResource(id = R.string.loading_details))
        }
        is CocktailDetailApiState.Error -> {
            Text(text = stringResource(id = R.string.error_details))
        }
        is CocktailDetailApiState.Succes -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(scrollState),
            ) {
                CocktailIngredientHeader(
                    onBack = onBack,
                    title = cocktailDetailApiState.cocktail.title,
                    isFavorite = cocktailDetailApiState.cocktail.isFavorite,
                    onStarClicked = { flag -> cocktailDetailViewModel.onFavoriteChanged(flag) },
                )

                cocktailDetailApiState.cocktail.category?.let { category ->
                    CocktailIngredientSpecifics(category)
                }
                cocktailDetailApiState.cocktail.alcoholFilter?.let { alcohol ->
                    CocktailIngredientSpecifics(alcohol)
                }
                cocktailDetailApiState.cocktail.typeOfGlass?.let { glass ->
                    CocktailIngredientSpecifics(glass)
                }

                CocktailDetailSectionSeparartor()

                CocktailDetailSubTitle(label = R.string.subtitle_ingredients, color = MaterialTheme.colorScheme.secondary)

                cocktailDetailApiState.cocktail.ingredientNames?.let { names ->
                    for ((i, ingredient) in names.withIndex()) {
                        cocktailDetailApiState.cocktail.measurements?.let { measurements ->
                            CocktailDetailIngredientRow(
                                ingredientName = ingredient,
                                measurement = if (i < measurements.size) measurements[i] else "",
                            )
                        }
                    }
                }

                CocktailDetailSectionSeparartor()

                CocktailDetailSubTitle(label = R.string.subtitle_instructions, color = MaterialTheme.colorScheme.secondary)
                cocktailDetailApiState.cocktail.instructions?.let { instructions ->
                    for ((i, instruction) in instructions.split(". ").withIndex()) {
                        CocktailIngredientInstructionRow(
                            i = i,
                            instruction = instruction,
                        )
                    }
                }
            }
        }
    }
}
