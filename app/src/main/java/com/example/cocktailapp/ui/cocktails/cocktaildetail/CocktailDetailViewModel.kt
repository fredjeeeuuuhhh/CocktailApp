package com.example.cocktailapp.ui.cocktails.cocktaildetail

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
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.ui.CocktailDestinationsArgs
import com.example.cocktailapp.ui.CocktailDetailApiState
import com.example.cocktailapp.ui.cocktails.cocktailoverview.CocktailOverviewViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException


class CocktailDetailViewModel (
    private val cocktailRepository: CocktailRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val cocktailId: String = savedStateHandle[CocktailDestinationsArgs.COCKTAIL_ID_ARG]!!

    private val _uiState = MutableStateFlow(
        CocktailDetailState(CocktailSampler.cocktails.find { cocktail: Cocktail -> cocktail.id == 15300 }!!),
    )
    val uiState: StateFlow<CocktailDetailState> = _uiState.asStateFlow()

    var cocktailDetailApiState: CocktailDetailApiState by  mutableStateOf( CocktailDetailApiState.Loading)
        private set

    init{
        getApiCocktail()
    }

    private fun getApiCocktail() {
        viewModelScope.launch {
            cocktailDetailApiState = try {
                val result = cocktailRepository.getCocktailById(cocktailId.toInt())
                _uiState.update { it.copy(currentCocktail = result) }
                CocktailDetailApiState.Succes(result)
            }catch (e: IOException){
                e.printStackTrace()
                CocktailDetailApiState.Error
            }
        }
    }

    fun onFavoriteChanged(flag:Boolean) {
        _uiState.value.currentCocktail.isFavorite=flag
    }

    companion object {
        private var Instance: CocktailDetailViewModel? = null
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                if (Instance == null) {
                    val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CocktailApplication)
                    val cocktailRepository = application.container.cocktailRepository
                    val savedStateHandle = createSavedStateHandle()
                    Instance = CocktailDetailViewModel(cocktailRepository = cocktailRepository, savedStateHandle = savedStateHandle)
                }
                Instance!!
            }
        }
    }
}
