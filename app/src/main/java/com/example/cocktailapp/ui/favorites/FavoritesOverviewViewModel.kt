package com.example.cocktailapp.ui.favorites

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesOverviewViewModel():ViewModel() {
    private val _uiState = MutableStateFlow(FavoritesOverviewState(emptyList()))
    val uiState: StateFlow<FavoritesOverviewState> = _uiState.asStateFlow()
}