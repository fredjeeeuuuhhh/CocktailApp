package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CocktailOverviewViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailOverviewState(CocktailSampler.getAll()))
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()
}
