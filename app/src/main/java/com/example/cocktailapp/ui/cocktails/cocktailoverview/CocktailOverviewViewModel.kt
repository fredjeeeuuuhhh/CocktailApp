package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class CocktailOverviewViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(
        CocktailOverviewState(CocktailSampler.cocktails, emptyList())
    )
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()
    //TODO get all ingredients and cocktails group by owned, display on cocktails number of owned ingredients
    fun setFilters(filters: List<String>) {
       _uiState.value.copy(
           currentFilters = filters
       )
    }
}
