package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailOverviewViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
   // private val _savedFilterType = savedStateHandle.getStateFlow(COCKTAIL_FILTER_SAVED_STATE_KEY, ALL_TASKS)

    private val _uiState = MutableStateFlow(CocktailOverviewState())
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()
    //TODO get all ingredients and cocktails group by owned, display on cocktails number of owned ingredients

    var cocktailApiState: CocktailApiState by mutableStateOf(CocktailApiState.Loading)
        private set

    lateinit var uiListState: StateFlow<List<Cocktail>>
    init{
        getRepoCocktails()
    }

    fun setFilters(filters: List<String>) {
        savedStateHandle[COCKTAIL_FILTER_SAVED_STATE_KEY] = filters.toTypedArray()
    }

    private fun getRepoCocktails() {
        try{
            uiListState = cocktailRepository.getAllCocktails()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = emptyList()
                )
            cocktailApiState = CocktailApiState.Success
        }catch (e: IOException){
            e.printStackTrace()
            cocktailApiState = CocktailApiState.Error
        }
    }
}
const val COCKTAIL_FILTER_SAVED_STATE_KEY = "COCKTAIL_FILTER_SAVED_STATE_KEY"