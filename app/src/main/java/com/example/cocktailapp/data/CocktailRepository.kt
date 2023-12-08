package com.example.cocktailapp.data

import com.example.cocktailapp.data.database.CocktailDao
import com.example.cocktailapp.data.database.asDbCocktail
import com.example.cocktailapp.data.database.asDbIngredientNames
import com.example.cocktailapp.data.database.asDbMeasurements
import com.example.cocktailapp.data.database.asDomainCocktail
import com.example.cocktailapp.data.database.asDomainCocktails
import com.example.cocktailapp.data.database.asDomainIngredientNames
import com.example.cocktailapp.data.database.asDomainMeasurements
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.getCocktailsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface CocktailRepository{
    suspend fun upsertCocktail(cocktail:Cocktail)
    suspend fun updateCocktail(cocktailId:Int, isFavorite:Boolean):Flow<Cocktail>
    fun getAllCocktails(): Flow<List<Cocktail>>
    suspend fun getCocktailById(id: Int): Flow<Cocktail>
    suspend fun getRandomCocktail(randomId: Int): Flow<Cocktail>
    suspend fun refresh()
}

class OfflineCocktailsRepository(
    private val cocktailDao: CocktailDao,
    private val cocktailApiService: CocktailApiService
): CocktailRepository{
    override suspend fun upsertCocktail(cocktail: Cocktail) {
        cocktailDao.insertCocktailWithMeasurementsAndIngredientNames(
            cocktail.asDbCocktail(),
            cocktail.measurements!!.asDbMeasurements(),
            cocktail.ingredientNames!!.asDbIngredientNames()
        )
    }

    override suspend fun updateCocktail(cocktailId: Int, isFavorite: Boolean): Flow<Cocktail> {
        cocktailDao.updateIsFavorite(cocktailId,isFavorite)
        val m =  cocktailDao.getItemM(cocktailId).measurements.asDomainMeasurements()
        val i:List<String> = cocktailDao.getItemI(cocktailId).ingredientNames.asDomainIngredientNames()
        return  cocktailDao.getItem(cocktailId).map {
            it.asDomainCocktail(m,i)
        }
    }

    override fun getAllCocktails(): Flow<List<Cocktail>> {
        return cocktailDao.getAllItems().map{
            it.asDomainCocktails()
        }.onEach {
            if (it.isEmpty()){
                refresh()
            }
        }
    }

    override suspend fun getCocktailById(id: Int): Flow<Cocktail> {

        val m =  cocktailDao.getItemM(id).measurements.asDomainMeasurements()
        val i:List<String> = cocktailDao.getItemI(id).ingredientNames.asDomainIngredientNames()
        return  cocktailDao.getItem(id).map {
            it.asDomainCocktail(m,i)
        }
    }

    override suspend fun getRandomCocktail(randomId:Int): Flow<Cocktail> {
        val m =  cocktailDao.getItemM(randomId).measurements.asDomainMeasurements()
        val i:List<String> = cocktailDao.getItemI(randomId).ingredientNames.asDomainIngredientNames()
        return  cocktailDao.getItem(randomId).map {
            it.asDomainCocktail(m,i)
        }
    }

    override suspend fun refresh() {
        val abc = "abcdefghijklmnopqrstuvwxyz"
        for(char in abc){
            cocktailApiService.getCocktailsAsFlow(char.toString()).collect {
                for (cocktail in it.drinks.asDomainObjects()){
                    upsertCocktail(cocktail)
                }
            }
        }
    }
}
