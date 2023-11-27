package com.example.cocktailapp.data

import com.example.cocktailapp.data.database.CocktailDao
import com.example.cocktailapp.data.database.asDbCocktail
import com.example.cocktailapp.data.database.asDomainCocktail
import com.example.cocktailapp.data.database.asDomainCocktails
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CocktailRepository{
    //suspend fun getCocktailsByFirstLetter(firstLetter: String): Flow<List<Cocktail>>
    suspend fun getAllCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktailById(id: Int): Flow<Cocktail>
    suspend fun getRandomCocktail(randomId: Int): Flow<Cocktail>
    //suspend fun searchByIngredient(ingredientName:String): Flow<List<Cocktail>>
    suspend fun upsertCocktail(cocktail:Cocktail)
    suspend fun updateCocktail(cocktailId:Int, isFavorite:Boolean)
}

class OfflineCocktailsRepository(
    private val cocktailDao: CocktailDao
): CocktailRepository{

    override suspend fun getAllCocktails(): Flow<List<Cocktail>> {
        return cocktailDao.getAllItems().map{
            it.asDomainCocktails()
        }
    }

    override suspend fun getCocktailById(id: Int): Flow<Cocktail> {
        return cocktailDao.getItem(id).map {
            it.asDomainCocktail()
        }
    }

    override suspend fun getRandomCocktail(randomId:Int): Flow<Cocktail> {
        return cocktailDao.getRandomItem(randomId).map{
            it.asDomainCocktail()
        }
    }

    override suspend fun upsertCocktail(cocktail: Cocktail) {
        cocktailDao.upsert(cocktail.asDbCocktail())
    }

    override suspend fun updateCocktail(cocktailId: Int, isFavorite: Boolean) {
        cocktailDao.updateIsFavorite(cocktailId,isFavorite)
    }

}
/*class CocktailApiRepository(
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
}*/