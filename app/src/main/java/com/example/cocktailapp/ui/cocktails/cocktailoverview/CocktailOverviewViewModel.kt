package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.local.cocktails.CocktailRepository
import com.example.cocktailapp.ui.CocktailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailOverviewViewModel(
    private val cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailOverviewState())
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()

    var cocktailApiState: CocktailApiState by mutableStateOf(CocktailApiState.Loading)
        private set
    init {
        viewModelScope.launch {
            cocktailRepository.refreshCocktails()
        }
        getApiCocktails()
    }

    private fun getApiCocktails() {
        viewModelScope.launch {
            cocktailRepository.getAll()
                .catch { exception ->
                    exception.printStackTrace()
                    cocktailApiState = CocktailApiState.Error
                }
                .collect { cocktails ->
                    cocktailApiState = CocktailApiState.Succes(
                        cocktails,
                    )
                }
        }
    }

    fun refreshCocktails() {
        _uiState.update {
            it.copy(isRefreshing = true)
        }
        cocktailApiState = CocktailApiState.Loading
        viewModelScope.launch {
            cocktailRepository.getAll()
                .catch { exception ->
                    exception.printStackTrace()
                    _uiState.update {
                        it.copy(isRefreshing = false)
                    }
                    cocktailApiState = CocktailApiState.Error
                }
                .collect { cocktails ->
                    cocktailApiState = CocktailApiState.Succes(
                        cocktails,
                    )
                    _uiState.update {
                        it.copy(isRefreshing = false)
                    }
                }
        }
    }

    companion object {
        private var Instance: CocktailOverviewViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[APPLICATION_KEY] as CocktailApplication)
                    val cocktailRepository = application.container.cocktailRepository
                    Instance = CocktailOverviewViewModel(cocktailRepository = cocktailRepository)
                }
                Instance!!
            }
        }
    }
}
