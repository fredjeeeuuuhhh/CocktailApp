package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CocktailDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val cocktailName: String = savedStateHandle[CocktailDestinationsArgs.COCKTAIL_NAME_ARG]!!

    private val _uiState = MutableStateFlow(
        CocktailDetailState(CocktailSampler.cocktails.find { cocktail: Cocktail -> cocktail.strDrink == cocktailName }!!),
    )
    val uiState: StateFlow<CocktailDetailState> = _uiState.asStateFlow()

    fun onFavoriteChanged(flag:Boolean) {
        _uiState.value.currentCocktail.isFavorite=flag
    }
}
