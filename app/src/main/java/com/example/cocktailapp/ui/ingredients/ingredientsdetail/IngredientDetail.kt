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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.IngredientDetailApiState
import com.example.cocktailapp.ui.cocktails.cocktaildetail.components.CocktailDetailSectionSeparartor
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.components.CocktailRowItem
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.components.IngredientHeader
import com.example.cocktailapp.ui.ingredients.ingredientsdetail.components.IngredientSpecifics

@Composable
fun IngredientDetail(
    onBack: ()-> Unit,
    ingredientDetailViewModel: IngredientDetailViewModel = hiltViewModel(),
    onViewDetailClicked: (Cocktail) -> Unit,
){
    val scrollState = rememberScrollState()
    val state by ingredientDetailViewModel.uiState.collectAsState()
    val listState = ingredientDetailViewModel.uiListState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(scrollState),
    ){
        val ingredientDetailApiState: IngredientDetailApiState = ingredientDetailViewModel.ingredientDetailApiState
        when(ingredientDetailApiState){
            is IngredientDetailApiState.Loading -> {
                Text("loading data from ingredient")
            }
            is IngredientDetailApiState.Error -> {
                Text("error while loading data from ingredient")
            }
            is IngredientDetailApiState.Success -> {
                if(state.isOwned==null) state.isOwned = false
                state.isOwned?.let {
                    IngredientHeader(
                        onBack,
                        state.name,
                        it
                    ) { flag -> ingredientDetailViewModel.onOwnedChanged(flag) }
                }
                state.containsAlcohol?.let { IngredientSpecifics("Contains alcohol: "+ if (it) "Yes" else "No") }
                state.type?.let { IngredientSpecifics("Type of alcohol: $it") }
                state.alcoholPercentage?.let { IngredientSpecifics("Alcohol percentage: $it%") }

                CocktailDetailSectionSeparartor()

                state.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Justify,
                    )
                    CocktailDetailSectionSeparartor()
                }

                LazyRow{
                    items(listState){cocktail->
                        CocktailRowItem(cocktail = cocktail, onViewDetailClicked =  {onViewDetailClicked(cocktail)})
                    }
                }
            }
        }

    }
}