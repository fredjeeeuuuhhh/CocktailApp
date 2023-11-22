package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktaiilIngredientHeader
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailIngredientRow
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSectionSeparartor
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSubTitle
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientInstructionRow
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientSpecifics

@Composable
fun CocktailDetail(
    onBack: () -> Unit,
    cocktailDetailViewModel: CocktailDetailViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val state by cocktailDetailViewModel.uiState.collectAsState()
    val cocktail = state.currentCocktail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState),
    ) {
        cocktail.isFavorite?.let {
            CocktaiilIngredientHeader(
                onBack,
                cocktail.title,
                it,
                {flag-> cocktailDetailViewModel.onFavoriteChanged(flag) },
            )
        }

        CocktailIngredientSpecifics(cocktail.category)
        CocktailIngredientSpecifics(cocktail.alcoholFilter)
        CocktailIngredientSpecifics(cocktail.typeOfGlass)

        CocktailDetailSectionSeparartor()

        CocktailDetailSubTitle(R.string.subtitle_ingredients,color=MaterialTheme.colorScheme.secondary)

        for ((i, ingredient) in cocktail.ingredientNames.withIndex()) {
            CocktailDetailIngredientRow(ingredient, cocktail.measurements[i])
        }

        CocktailDetailSectionSeparartor()

        CocktailDetailSubTitle(R.string.subtitle_instructions,color= MaterialTheme.colorScheme.secondary)

        for ((i, instruction) in cocktail.instructions.split(". ").withIndex()) {
            CocktailIngredientInstructionRow(i,instruction)
        }
    }
}
