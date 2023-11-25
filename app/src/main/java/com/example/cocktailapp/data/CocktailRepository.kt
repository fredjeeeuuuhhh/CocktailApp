package com.example.cocktailapp.data

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsFromSearch

interface CocktailRepository{
    suspend fun getCocktailsByFirstLetter(firstLetter: String): List<Cocktail>
    suspend fun getCocktailById(id: Int): Cocktail
    suspend fun getRandomCocktail(): Cocktail
    suspend fun searchByIngredient(ingredientName:String): List<Cocktail>
}
class CocktailApiRepository(
    val cocktailApiService: CocktailApiService
): CocktailRepository{
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): List<Cocktail> =
        cocktailApiService.getCocktails(firstLetter).drinks.asDomainObjects()
    override suspend fun getCocktailById(id: Int): Cocktail =
        cocktailApiService.getCocktailById(id).drinks.asDomainObject()
    override suspend fun getRandomCocktail(): Cocktail =
        cocktailApiService.getRandomCocktail().drinks.asDomainObject()
    override suspend fun searchByIngredient(ingredientName: String): List<Cocktail> =
        cocktailApiService.searchByIngredient(ingredientName).drinks.asDomainObjectsFromSearch()
}