package com.example.cocktailapp.ui.ingredients.ingredientsdetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.network.IngredientDetailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
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

    var ingredientDetailApiState: IngredientDetailApiState by mutableStateOf(
        IngredientDetailApiState.Loading)
        private set

    lateinit var uiState: StateFlow<Ingredient>
    //lateinit var uiListState: StateFlow<List<Cocktail>>
    var uiListState: List<Cocktail> by mutableStateOf(
        emptyList()
    )
        private set
    init{
        getIngredientDetails()
        getIngredientsCocktails()
    }

    private fun getIngredientsCocktails() {
        viewModelScope.launch {
            try{
                uiListState = ingredientRepository.getCocktailsForIngredient(ingredientName)
                for(c in uiListState){
                    Log.d("tag",c.toString())
                }
                ingredientDetailApiState = IngredientDetailApiState.Success
            } catch(e:IOException){
                e.printStackTrace()
                ingredientDetailApiState = IngredientDetailApiState.Error
            }
        }

    }

    private fun getIngredientDetails() {
        try{
            uiState = ingredientRepository.getItem(ingredientName)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = Ingredient(0,"","","",null,"","",null)
                )
            ingredientDetailApiState = IngredientDetailApiState.Success
        } catch(e:IOException){
            e.printStackTrace()
            ingredientDetailApiState = IngredientDetailApiState.Error
        }
    }

    fun onOwnedChanged(flag:Boolean) {
        viewModelScope.launch {
            try{
                uiState.value.let { ingredientRepository.updateIsOwned(it.id!!,flag) }
            }catch (e: IOException){
                e.printStackTrace()
                ingredientDetailApiState = IngredientDetailApiState.Error
            }
        }
    }
}