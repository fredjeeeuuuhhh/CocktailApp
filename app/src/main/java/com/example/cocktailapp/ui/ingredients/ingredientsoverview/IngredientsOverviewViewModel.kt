package com.example.cocktailapp.ui.ingredients.ingredientsoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.IngredientRepository
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class IngredientsOverviewViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository
): ViewModel() {

    var ingredientApiState: IngredientApiState by mutableStateOf(IngredientApiState.Loading)
        private set
    lateinit var uiListState: StateFlow<List<Ingredient>>
    init{
        getApiIngredients()
    }

    private fun getApiIngredients(){
        try{
            uiListState = ingredientRepository.getAllItems()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = emptyList()
                )
            ingredientApiState = IngredientApiState.Success
        } catch(e:IOException){
            e.printStackTrace()
            ingredientApiState = IngredientApiState.Error
        }
    }
}