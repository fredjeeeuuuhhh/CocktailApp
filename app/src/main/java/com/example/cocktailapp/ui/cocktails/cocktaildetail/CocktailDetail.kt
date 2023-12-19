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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.R
import com.example.cocktailapp.network.CocktailDetailApiState
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktaiilIngredientHeader
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailIngredientRow
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSectionSeparartor
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSubTitle
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientInstructionRow
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailIngredientSpecifics

@Composable
fun CocktailDetail(
    onBack: () -> Unit,
    cocktailDetailViewModel: CocktailDetailViewModel = viewModel(factory = CocktailDetailViewModel.Factory)
) {
    val scrollState = rememberScrollState()
    val state by cocktailDetailViewModel.uiState.collectAsState()
    val measurementsListState by cocktailDetailViewModel.uiMeasurementListState.collectAsState()
    val ingredientListState by cocktailDetailViewModel.uiIngredientNameListState.collectAsState()

    val cocktailDetailApiState = cocktailDetailViewModel.cocktailDetailApiState
    when(cocktailDetailApiState) {
        is CocktailDetailApiState.Loading -> {
            Text("Loading the details")
        }
        is CocktailDetailApiState.Error -> {
            Text("Something went wrong while loading cocktail details from api")
        }
        is CocktailDetailApiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(scrollState),
            ) {
                if(state.isFavorite ==null) state.isFavorite =false

                CocktaiilIngredientHeader(
                    onBack,
                    state.title,
                    state.isFavorite!!,
                ) { flag -> cocktailDetailViewModel.onFavoriteChanged(flag) }

                state.category?.let { CocktailIngredientSpecifics(it) }
                state.alcoholFilter?.let { CocktailIngredientSpecifics(it) }
                state.typeOfGlass?.let { CocktailIngredientSpecifics(it) }

                CocktailDetailSectionSeparartor()

                CocktailDetailSubTitle(R.string.subtitle_ingredients,color=MaterialTheme.colorScheme.secondary)

                ingredientListState.let{
                    for ((i, ingredient) in it.withIndex()) {
                        measurementsListState.let {  CocktailDetailIngredientRow(ingredient, if(i<it.size) it[i] else "") }
                    }
                }

                CocktailDetailSectionSeparartor()

                CocktailDetailSubTitle(R.string.subtitle_instructions,color= MaterialTheme.colorScheme.secondary)
                state.instructions?.let{
                    for ((i, instruction) in it.split(". ").withIndex()) {
                        CocktailIngredientInstructionRow(i,instruction)
                    }
                }
            }
        }

    }

}
