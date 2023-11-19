package com.example.cocktailapp.ui.cocktails.cocktaildetail

import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.model.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CocktailDetailViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(
        CocktailDetailState(
            Cocktail(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                emptyList(),
                emptyList(),
            ),
        ),
    )
    val uiState: StateFlow<CocktailDetailState> = _uiState.asStateFlow()

    fun setCurrentCocktail(name: String) {
        var c = CocktailSampler.getAll().find { c -> c.strDrink == name }
        _uiState.update {
            it.copy(currentCocktail = c!!)
        }
    }
}