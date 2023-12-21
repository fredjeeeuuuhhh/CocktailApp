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
import com.example.cocktailapp.local.ingredients.IngredientRepository
import com.example.cocktailapp.ui.IngredientApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngredientsOverviewViewModel(
    private val ingredientRepository: IngredientRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(IngredientsOverviewState(false, null))
    val uiState: StateFlow<IngredientsOverviewState> = _uiState.asStateFlow()

    var ingredientApiState: IngredientApiState by mutableStateOf(IngredientApiState.Loading)
        private set
    init {
        getApiIngredients()
    }

    private fun getApiIngredients() {
        viewModelScope.launch {
            ingredientRepository.getIngredients()
                .catch { exception ->
                    exception.printStackTrace()
                    ingredientApiState = IngredientApiState.Error
                }
                .collect { ingredients ->
                    ingredientApiState = IngredientApiState.Succes(ingredients)
                    _uiState.update { it.copy(currentIngredientList = ingredients) }
                }
        }
    }

    fun refreshIngredients() {
        _uiState.update {
            it.copy(isRefreshing = true)
        }
        viewModelScope.launch {
            ingredientRepository.getIngredients()
                .catch {
                    _uiState.update {
                        it.copy(isRefreshing = false)
                    }
                }
                .collect { ingredients ->
                    ingredientApiState = IngredientApiState.Succes(
                        ingredients,
                    )
                    _uiState.update {
                        it.copy(currentIngredientList = ingredients, isRefreshing = false)
                    }
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
