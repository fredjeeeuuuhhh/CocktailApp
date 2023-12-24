package com.example.cocktailapp.fake

import com.example.cocktailapp.network.ApiCocktailForSearch
import com.example.cocktailapp.network.ApiDrinks
import com.example.cocktailapp.network.CocktailApiSearchResult
import com.example.cocktailapp.network.CocktailApiService

class FakeCocktailApiService : CocktailApiService {
    override suspend fun getCocktails(firstLetter: String): ApiDrinks {
        return ApiDrinks(FakeDataSource.cocktail)
    }

    override suspend fun getCocktailById(id: Int): ApiDrinks {
        return ApiDrinks(listOf(FakeDataSource.cocktail.find { it.idDrink.toInt() == id }!!))
    }

    override suspend fun searchByIngredient(ingredient: String): CocktailApiSearchResult {
        val cocktails = FakeDataSource.cocktail.filter { it.strIngredient1 == ingredient || it.strIngredient2 == ingredient }
        return CocktailApiSearchResult(cocktails.map { ApiCocktailForSearch(it.strDrink, it.strDrinkThumb, it.idDrink) })
    }
}
