package com.example.cocktailapp.ui.cocktails.cocktailoverview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cocktailapp.data.CocktailSampler
import com.example.cocktailapp.model.Cocktail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CocktailOverviewViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(CocktailOverviewState(CocktailSampler.cocktails))
    val uiState: StateFlow<CocktailOverviewState> = _uiState.asStateFlow()

    fun favoritesValueChanged(flag: Boolean) {
            if(!flag){
                _uiState.update {
                    it.copy(currentCocktailList = CocktailSampler.cocktails)
                }
                Log.d("test",_uiState.value.currentCocktailList.size.toString())
            }else{

                _uiState.update {prev->
                    prev.copy(
                        currentCocktailList= prev.currentCocktailList.filter { cocktail: Cocktail -> cocktail.isFavorite==true }
                    )
                }
                Log.d("test",_uiState.value.currentCocktailList.size.toString())
            }
    }

    fun AlcoholValueChanged(flag: Boolean) {
        if(!flag){
            _uiState.update {
                it.copy(currentCocktailList = CocktailSampler.cocktails)
            }
            Log.d("test",_uiState.value.currentCocktailList.size.toString())
        }else{

            _uiState.update {prev->
              prev.copy(
                    currentCocktailList= prev.currentCocktailList.filter { cocktail: Cocktail -> cocktail.strAlcoholic =="Alcoholic" }
              )
            }
            Log.d("test",_uiState.value.currentCocktailList.size.toString())
        }
    }

    fun NonAlcoholValueChanged(flag: Boolean) {
        if(!flag){
            _uiState.update {
                it.copy(currentCocktailList = CocktailSampler.cocktails)
            }
            Log.d("test",_uiState.value.currentCocktailList.size.toString())
        }else{

            _uiState.update {prev->
                prev.copy(
                    currentCocktailList= prev.currentCocktailList.filter { cocktail: Cocktail -> cocktail.strAlcoholic =="Non alcoholic" }
                )
            }
            Log.d("test",_uiState.value.currentCocktailList.size.toString())
        }
    }
}
