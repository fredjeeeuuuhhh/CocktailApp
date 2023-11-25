package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.data.IngredientSampler
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.IngredientDetailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class IngredientDetailViewModel @Inject constructor(
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
}