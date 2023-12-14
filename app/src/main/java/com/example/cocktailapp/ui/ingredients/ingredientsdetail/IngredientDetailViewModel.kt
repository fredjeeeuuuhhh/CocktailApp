package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.IngredientDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class IngredientDetailViewModel(
    private val cocktailRepository: CocktailRepository,
    private val ingredientRepository: IngredientRepository,
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val ingredientName:String = savedStateHandle[CocktailDestinationsArgs.INGREDIENT_NAME_ARG]!!

    private val _uiState = MutableStateFlow(
        IngredientDetailState(
            IngredientSampler.ingredients.find { ingredient:Ingredient -> ingredient.id==1 }!!,
            CocktailSampler.cocktails//all cocktails for now, has to be api call
        )
    )
    val uiState: StateFlow<IngredientDetailState> = _uiState.asStateFlow()
    var ingredientDetailApiState: IngredientDetailApiState by mutableStateOf(IngredientDetailApiState.Loading)
        private set

    init{
        getIngredientDetails()
    }

    private fun getIngredientDetails() {
        viewModelScope.launch {
            ingredientDetailApiState = try{
                val ingredientResult = ingredientRepository.getIngredientByName(ingredientName)
                val cocktailResult = cocktailRepository.searchByIngredient(ingredientName)
                _uiState.update {
                    it.copy(
                        currentIngredient = ingredientResult,
                        cocktailsContainingIngredient = cocktailResult
                    )
                }
                IngredientDetailApiState.Succes(
                    ingredientResult,
                    cocktailsContainingIngredient = cocktailResult
                )
            }catch(e: IOException){
                e.printStackTrace()
                IngredientDetailApiState.Error
            }
        }
    }



    fun onOwnedChanged(flag:Boolean) {
        _uiState.value.currentIngredient.isOwned=flag
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        private var Instance: IngredientDetailViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CocktailApplication)
                    val ingredientRepository = application.container.ingredientRepository
                    val cocktailRepository = application.container.cocktailRepository
                    val savedStateHandle = createSavedStateHandle()
                    Instance = IngredientDetailViewModel(ingredientRepository = ingredientRepository, cocktailRepository = cocktailRepository, savedStateHandle = savedStateHandle)
                }
                Instance!!
            }
        }
    }
}