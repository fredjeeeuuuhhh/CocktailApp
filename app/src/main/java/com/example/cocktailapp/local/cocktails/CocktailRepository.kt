package com.example.cocktailapp.local.cocktails

import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CocktailRepository{
    suspend fun getCocktailsByFirstLetter(firstLetter: String): Flow<List<Cocktail>>
    suspend fun getCocktailById(id: Int): Flow<Cocktail>
    suspend fun searchByIngredient(ingredientName:String): Flow<List<Cocktail>>
}
class CocktailApiRepository(
    private val cocktailApiService: CocktailApiService
): CocktailRepository {
    override suspend fun getCocktailsByFirstLetter(firstLetter: String): Flow<List<Cocktail>> = flow{
        emit(cocktailApiService.getCocktails(firstLetter).drinks.asDomainObjects())
    }

    override suspend fun getCocktailById(id: Int): Flow<Cocktail> = flow{
        emit( cocktailApiService.getCocktailById(id).drinks.asDomainObject() )
    }

    override suspend fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>> = flow{
        emit( cocktailApiService.searchByIngredient(ingredientName).drinks.asDomainObjectsFromSearch() )
    }

}