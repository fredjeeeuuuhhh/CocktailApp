package com.example.cocktailapp.ui.cocktails.cocktailoverview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CocktailOverviewViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailOverviewState(CocktailSampler.cocktails))
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()

    fun setFilters(filters: List<String>) {
        //TODO based on filters cocktails are updated
        filters.forEach{
            Log.d("filters",it)
        }

    }
}
