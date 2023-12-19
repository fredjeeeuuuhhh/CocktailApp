package com.example.cocktailapp.ui.cocktails.cocktaildetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapp.CocktailApplication
import com.example.cocktailapp.local.cocktails.CocktailRepository
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.CocktailDetailApiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailDetailViewModel(
    private val cocktailRepository: CocktailRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val cocktailId: String = savedStateHandle[CocktailDestinationsArgs.COCKTAIL_ID_ARG]!!

    private val _uiState = MutableStateFlow(CocktailDetailState(null))
    val uiState: StateFlow<CocktailDetailState> = _uiState.asStateFlow()

    var cocktailDetailApiState: CocktailDetailApiState by mutableStateOf(CocktailDetailApiState.Loading)
        private set

    init {
        Log.i("vm inspection", cocktailId)
        getApiCocktail()
    }
    override fun onCleared() {
        super.onCleared()
        Log.i("vm inspection", cocktailId)
    }
    private fun getApiCocktail() {
        viewModelScope.launch {
            cocktailRepository.getCocktailById(cocktailId.toInt())
                .catch { exception ->
                    exception.printStackTrace()
                    cocktailDetailApiState = CocktailDetailApiState.Error
                }
                .collect { cocktail ->
                    cocktailDetailApiState = CocktailDetailApiState.Succes(cocktail)
                    _uiState.update { it.copy(currentCocktail = cocktail) }
                }
        }
    }

    fun onFavoriteChanged(flag: Boolean) {
        viewModelScope.launch {
            cocktailRepository.updateIsFavorite(cocktailId.toInt(), flag)
                .catch { exception ->
                    exception.printStackTrace()
                    cocktailDetailApiState = CocktailDetailApiState.Error
                }.collect {}
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CocktailApplication)
                val cocktailRepository = application.container.cocktailRepository
                val savedStateHandle = createSavedStateHandle()
                CocktailDetailViewModel(cocktailRepository = cocktailRepository, savedStateHandle = savedStateHandle)
            }
        }
    }
}
