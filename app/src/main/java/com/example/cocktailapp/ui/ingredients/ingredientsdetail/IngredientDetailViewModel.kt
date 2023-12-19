package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import android.util.Log
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
import com.example.cocktailapp.local.cocktails.CocktailRepository
import com.example.cocktailapp.local.ingredients.IngredientRepository
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.IngredientDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngredientDetailViewModel(
    private val cocktailRepository: CocktailRepository,
    private val ingredientRepository: IngredientRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val ingredientName: String = savedStateHandle[CocktailDestinationsArgs.INGREDIENT_NAME_ARG]!!

    private val _uiState = MutableStateFlow(IngredientDetailState(null, null))
    val uiState: StateFlow<IngredientDetailState> = _uiState.asStateFlow()
    var ingredientDetailApiState: IngredientDetailApiState by mutableStateOf(IngredientDetailApiState.Loading)
        private set

    init {
        getIngredientDetails()
    }

    private fun getIngredientDetails() {
        viewModelScope.launch {
            Log.i("name", ingredientName)
            ingredientRepository.getIngredientByName(ingredientName)
                .combine(cocktailRepository.searchByIngredient(ingredientName)) {
                        ingredients, cocktails ->
                    ingredientDetailApiState = IngredientDetailApiState.Succes(
                        ingredients,
                        cocktails,
                    )
                    _uiState.update { it.copy(currentIngredient = ingredients, cocktailsContainingIngredient = cocktails) }
                }
                .catch { exception ->
                    exception.printStackTrace()
                    ingredientDetailApiState = IngredientDetailApiState.Error
                }
                .collect {}
        }
    }
    fun onOwnedChanged(flag: Boolean) {
        viewModelScope.launch {
            ingredientRepository.updateIsOwned(ingredientName, flag)
                .catch { exception ->
                    exception.printStackTrace()
                    ingredientDetailApiState = IngredientDetailApiState.Error
                }.collect {}
        }
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CocktailApplication)
                val ingredientRepository = application.container.ingredientRepository
                val cocktailRepository = application.container.cocktailRepository
                val savedStateHandle = createSavedStateHandle()
                IngredientDetailViewModel(ingredientRepository = ingredientRepository, cocktailRepository = cocktailRepository, savedStateHandle = savedStateHandle)
            }
        }
    }
}
