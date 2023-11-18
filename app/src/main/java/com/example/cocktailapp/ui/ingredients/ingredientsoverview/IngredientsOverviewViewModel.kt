package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Ingredient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IngredientsOverviewViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(IngredientsOverviewState(IngredientSampler.getIngredients()))
    val uiState: StateFlow<IngredientsOverviewState> = _uiState.asStateFlow()

    fun changeOwnedStatus(ingredient: Ingredient) {
        var ing = _uiState.value.currentIngredientList.find { i->i.strIngredient==ingredient.strIngredient }
        ing?.isOwned=(!ing?.isOwned!!)
    }
}