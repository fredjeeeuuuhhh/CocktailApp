package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApi
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsWithNameOnly
import com.example.cocktailapp.ui.IngredientApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class IngredientsOverviewViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(IngredientsOverviewState(IngredientSampler.ingredients))
    val uiState: StateFlow<IngredientsOverviewState> = _uiState.asStateFlow()

    var ingredientApiState: IngredientApiState by mutableStateOf(IngredientApiState.Loading)
        private set
    init{
        getApiIngredients()
    }

    fun changeOwnedStatus(ingredient: Ingredient) {
        var ing = _uiState.value.currentIngredientList.find { i->i.name==ingredient.name }
        ing?.isOwned=(!ing?.isOwned!!)
    }

    private fun getApiIngredients(){
        viewModelScope.launch {
            ingredientApiState = try {
                val result = IngredientApi.ingredientService.getIngredients()
                _uiState.update { it.copy(currentIngredientList = result.drinks.asDomainObjectsWithNameOnly() ) }
                IngredientApiState.Succes(result.drinks.asDomainObjectsWithNameOnly())
            }catch(e:IOException){
                e.printStackTrace()
                IngredientApiState.Error
            }
        }
    }
}