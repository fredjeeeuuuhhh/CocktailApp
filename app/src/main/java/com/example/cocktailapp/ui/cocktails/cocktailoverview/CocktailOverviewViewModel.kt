package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.ui.CocktailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class CocktailOverviewViewModel (
    private val cocktailRepository: CocktailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _savedFilterType = savedStateHandle.getStateFlow(COCKTAIL_FILTER_SAVED_STATE_KEY, emptyList<String>())

    private val _uiState = MutableStateFlow(CocktailOverviewState(CocktailSampler.cocktails))
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()

    var cocktailApiState: CocktailApiState by mutableStateOf(CocktailApiState.Loading)
        private set
    init{
        getApiCocktails()
    }

    fun setFilters(filters: List<String>) {
        savedStateHandle[COCKTAIL_FILTER_SAVED_STATE_KEY] = filters.toTypedArray()
    }

    private fun getApiCocktails() {
        viewModelScope.launch {
            cocktailApiState = try{
                val result = cocktailRepository.getCocktailsByFirstLetter("a")
                _uiState.update {
                    it.copy(currentCocktailList = result)
                }
                CocktailApiState.Succes(result)
            }catch (e: IOException){
                e.printStackTrace()
                CocktailApiState.Error
            }
        }
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        private var Instance: CocktailOverviewViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as CocktailApplication)
                    val cocktailRepository = application.container.cocktailRepository
                    val savedStateHandle = createSavedStateHandle()
                    Instance = CocktailOverviewViewModel(cocktailRepository = cocktailRepository, savedStateHandle = savedStateHandle)
                }
                Instance!!
            }
        }
    }
}
const val COCKTAIL_FILTER_SAVED_STATE_KEY = "COCKTAIL_FILTER_SAVED_STATE_KEY"