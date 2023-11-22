package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IngredientsOverviewViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(IngredientsOverviewState(IngredientSampler.ingredients))
    val uiState: StateFlow<IngredientsOverviewState> = _uiState.asStateFlow()

    init{
        //getApiIngredients()
    }

    fun changeOwnedStatus(ingredient: Ingredient) {
        var ing = _uiState.value.currentIngredientList.find { i->i.name==ingredient.name }
        ing?.isOwned=(!ing?.isOwned!!)
    }

    private fun getApiIngredients(){
        //viewModelScope.launch {

        //}
    }
}