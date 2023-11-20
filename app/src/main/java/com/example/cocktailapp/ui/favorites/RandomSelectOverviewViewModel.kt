package com.example.cocktailapp.ui.favorites

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RandomSelectOverviewViewModel():ViewModel() {
    private val _uiState = MutableStateFlow(RandomSelectOverviewState(null))
    val uiState: StateFlow<RandomSelectOverviewState> = _uiState.asStateFlow()
}