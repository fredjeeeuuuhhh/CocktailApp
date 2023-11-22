package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class IngredientDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val ingredientName:String = savedStateHandle[CocktailDestinationsArgs.INGREDIENT_NAME_ARG]!!

    private val _uiState = MutableStateFlow(
        IngredientDetailState(
            IngredientSampler.ingredients.find { ingredient:Ingredient -> ingredient.name==ingredientName }!!,
            CocktailSampler.cocktails.filter { cocktail: Cocktail ->  cocktail.ingredientNames.contains(ingredientName)}
        )
    )

    val uiState: StateFlow<IngredientDetailState> = _uiState.asStateFlow()

    fun onOwnedChanged(flag:Boolean) {
        _uiState.value.currentIngredient.isOwned=flag
    }
}