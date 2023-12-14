package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.IngredientApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class IngredientsOverviewViewModel (
    private val ingredientRepository: IngredientRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(IngredientsOverviewState(IngredientSampler.ingredients))
    val uiState: StateFlow<IngredientsOverviewState> = _uiState.asStateFlow()

    var ingredientApiState: IngredientApiState by mutableStateOf(IngredientApiState.Loading)
        private set
    init{
        getApiIngredients()
    }

    fun changeOwnedStatus(ingredient: Ingredient) {
        val ing = _uiState.value.currentIngredientList.find { i->i.name==ingredient.name }
        ing?.isOwned=(!ing?.isOwned!!)
    }

    private fun getApiIngredients(){
        viewModelScope.launch {
            ingredientApiState = try {
                val result = ingredientRepository.getIngredients()
                _uiState.update { it.copy(currentIngredientList = result ) }
                IngredientApiState.Succes(result)
            }catch(e:IOException){
                e.printStackTrace()
                IngredientApiState.Error
            }
        }
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        private var Instance: IngredientsOverviewViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CocktailApplication)
                    val ingredientRepository = application.container.ingredientRepository
                    Instance = IngredientsOverviewViewModel(ingredientRepository = ingredientRepository)
                }
                Instance!!
            }
        }
    }
}