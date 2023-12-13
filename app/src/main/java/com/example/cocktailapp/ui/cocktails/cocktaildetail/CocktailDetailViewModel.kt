package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.network.CocktailDetailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val cocktailId: String = savedStateHandle[CocktailDestinationsArgs.COCKTAIL_ID_ARG]!!
    var cocktailDetailApiState: CocktailDetailApiState by  mutableStateOf( CocktailDetailApiState.Loading)
        private set
    lateinit var uiState: StateFlow<Cocktail>
    lateinit var uiMeasurementListState: StateFlow<List<String>>
    lateinit var uiIngredientNameListState: StateFlow<List<String>>
    init{
        getApiCocktail()
        getMeasurements()
        getIngredientNames()
    }

    private fun getIngredientNames() {
        try{
            uiIngredientNameListState = cocktailRepository.getCocktailIngredientNames(cocktailId.toInt())
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = emptyList(),
                )
            cocktailDetailApiState = CocktailDetailApiState.Success
        }catch(e: IOException){
            e.printStackTrace()
            cocktailDetailApiState = CocktailDetailApiState.Error
        }
    }

    private fun getMeasurements() {
        try{
            uiMeasurementListState = cocktailRepository.getCocktailMeasurements(cocktailId.toInt())
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = emptyList(),
                )
            cocktailDetailApiState = CocktailDetailApiState.Success
        }catch(e: IOException){
            e.printStackTrace()
            cocktailDetailApiState = CocktailDetailApiState.Error
        }
    }

    private fun getApiCocktail() {
        try{
            uiState = cocktailRepository.getCocktailById(cocktailId.toInt())
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = Cocktail(1,"","","","","","", emptyList(),emptyList()),
                )
            cocktailDetailApiState = CocktailDetailApiState.Success
        }catch(e: IOException){
            e.printStackTrace()
            cocktailDetailApiState = CocktailDetailApiState.Error
        }
    }

    fun onFavoriteChanged(flag:Boolean) {
        viewModelScope.launch {
            try{
                uiState.value.let { cocktailRepository.updateCocktail(it.id,flag) }
            }catch (e: IOException){
                e.printStackTrace()
                cocktailDetailApiState = CocktailDetailApiState.Error
            }
        }
    }
}
