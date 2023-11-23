package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApi
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.CocktailDetailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val cocktailId: String = savedStateHandle[CocktailDestinationsArgs.COCKTAIL_ID_ARG]!!

    private val _uiState = MutableStateFlow(
        CocktailDetailState(CocktailSampler.cocktails.find { cocktail: Cocktail -> cocktail.id == 15300 }!!),
    )
    val uiState: StateFlow<CocktailDetailState> = _uiState.asStateFlow()

    var cocktailDetailApiState: CocktailDetailApiState by  mutableStateOf( CocktailDetailApiState.Loading)
        private set

    init{
        getApiCocktail()
    }

    private fun getApiCocktail() {
        viewModelScope.launch {
            cocktailDetailApiState = try {
                val result = CocktailApi.cocktailService.getCocktailById(cocktailId.toInt())
                _uiState.update { it.copy(currentCocktail = result.drinks.asDomainObject()) }
                CocktailDetailApiState.Succes(result.drinks.asDomainObject())
            }catch (e: IOException){
                e.printStackTrace()
                CocktailDetailApiState.Error
            }
        }
    }

    fun onFavoriteChanged(flag:Boolean) {
        _uiState.value.currentCocktail.isFavorite=flag
    }
}
