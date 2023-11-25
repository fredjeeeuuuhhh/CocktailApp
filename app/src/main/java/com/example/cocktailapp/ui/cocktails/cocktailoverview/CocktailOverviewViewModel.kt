package com.example.cocktailapp.ui.cocktails.cocktailoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailapp.data.CocktailRepository
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.ui.CocktailApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailOverviewViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
   // private val _savedFilterType = savedStateHandle.getStateFlow(COCKTAIL_FILTER_SAVED_STATE_KEY, ALL_TASKS)

    private val _uiState = MutableStateFlow(CocktailOverviewState(CocktailSampler.cocktails))
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()
    //TODO get all ingredients and cocktails group by owned, display on cocktails number of owned ingredients

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

    /* private fun getFilterUiInfo(requestTypes: List<String>): FilteringUiInfo {
        for (requestType in requestTypes){
           when (requestType) {
                "All" -> {
                    FilteringUiInfo(
                        R.string.label_all, R.string.no_tasks_all,
                        R.drawable.logo_no_fill
                    )
                }
                "Alcoholic" -> {
                    FilteringUiInfo(
                        R.string.label_active, R.string.no_tasks_active,
                        R.drawable.ic_check_circle_96dp
                    )
                }
                "Non alcoholic" -> {
                    FilteringUiInfo(
                        R.string.label_completed, R.string.no_tasks_completed,
                        R.drawable.ic_verified_user_96dp
                    )
                }
            }

        }
    }*/

}
const val COCKTAIL_FILTER_SAVED_STATE_KEY = "COCKTAIL_FILTER_SAVED_STATE_KEY"