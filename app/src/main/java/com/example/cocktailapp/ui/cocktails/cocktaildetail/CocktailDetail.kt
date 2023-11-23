package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.ui.CocktailDetailApiState
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

    val cocktailDetailApiState = cocktailDetailViewModel.cocktailDetailApiState
    when(cocktailDetailApiState) {
        is CocktailDetailApiState.Loading -> {
            Text("Loading the details")
        }
        is CocktailDetailApiState.Error -> {
            Text("Something went wrong while loading cocktail details from api")
        }
        is CocktailDetailApiState.Succes -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(scrollState),
            ) {
                if(cocktailDetailApiState.cocktail.isFavorite==null)cocktailDetailApiState.cocktail.isFavorite=false

                CocktaiilIngredientHeader(
                    onBack,
                    cocktailDetailApiState.cocktail.title,
                    cocktailDetailApiState.cocktail.isFavorite!!,
                ) { flag -> cocktailDetailViewModel.onFavoriteChanged(flag) }

                cocktailDetailApiState.cocktail.category?.let { CocktailIngredientSpecifics(it) }
                cocktailDetailApiState.cocktail.alcoholFilter?.let { CocktailIngredientSpecifics(it) }
                cocktailDetailApiState.cocktail.typeOfGlass?.let { CocktailIngredientSpecifics(it) }

                CocktailDetailSectionSeparartor()

                CocktailDetailSubTitle(R.string.subtitle_ingredients,color=MaterialTheme.colorScheme.secondary)

                cocktailDetailApiState.cocktail.ingredientNames?.let{
                    for ((i, ingredient) in it.withIndex()) {
                        cocktailDetailApiState.cocktail.measurements?.let {  CocktailDetailIngredientRow(ingredient, it[i]) }
                    }
                }

                CocktailDetailSectionSeparartor()

                CocktailDetailSubTitle(R.string.subtitle_instructions,color= MaterialTheme.colorScheme.secondary)
                cocktailDetailApiState.cocktail.instructions?.let{
                    for ((i, instruction) in it.split(". ").withIndex()) {
                        CocktailIngredientInstructionRow(i,instruction)
                    }
                }
            }
        }
    }

}
