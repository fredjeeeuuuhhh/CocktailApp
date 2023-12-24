package com.example.cocktailapp.fake

import com.example.cocktailapp.local.cocktails.CocktailRepository
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.ApiCocktailForSearch
import com.example.cocktailapp.network.ApiDrinks
import com.example.cocktailapp.network.CocktailApiSearchResult
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeApiCocktailRepository() : CocktailRepository {
    override fun getAll(): Flow<List<Cocktail>> = flow {
        emit(ApiDrinks(FakeDataSource.cocktail).drinks!!.asDomainObjects())
    }

    override fun getCocktailById(id: Int): Flow<Cocktail> = flow {
        emit(ApiDrinks(FakeDataSource.cocktail).drinks!!.asDomainObjects().find { it.id == id }!!)
    }

    override fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>> = flow {
        emit(
            CocktailApiSearchResult(
                FakeDataSource.cocktail.filter { it.strIngredient1 == ingredientName || it.strIngredient2 == ingredientName }
                    .map {
                        ApiCocktailForSearch(
                            it.strDrink,
                            it.strDrinkThumb,
                            it.idDrink,
                        )
                    },
            ).drinks.asDomainObjectsFromSearch(),
        )
    }

    override suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void> = flow {
    }

    override suspend fun refreshCocktails() {
    }

    override suspend fun refreshCocktailsInWorker() {
    }

    override suspend fun getCocktailByIdInWorker(id: Int) {
    }

    override suspend fun searchByIngredientInWorker(ingredientName: String) {
    }
}
