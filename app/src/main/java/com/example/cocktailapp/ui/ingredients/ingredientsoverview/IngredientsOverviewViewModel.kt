package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.IngredientSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class IngredientsOverviewViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(IngredientsOverviewState(IngredientSampler.getIngredients()))
    val uiState: StateFlow<IngredientsOverviewState> = _uiState.asStateFlow()
}