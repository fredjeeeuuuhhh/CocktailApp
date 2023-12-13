package com.example.cocktailapp.data

import android.util.Log
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
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toSet

interface CocktailRepository{
    suspend fun insertCocktail(cocktail:Cocktail)
    suspend fun updateCocktail(cocktailId:Int, isFavorite:Boolean)
    fun getAllCocktails(): Flow<List<Cocktail>>
    fun getCocktailById(id: Int): Flow<Cocktail>
    suspend fun refresh()
    fun getCocktailsByCategory(filters: List<String>): Flow<List<Cocktail>>
    fun getCocktailMeasurements(id: Int):Flow<List<String>>
    fun getCocktailIngredientNames(id: Int): Flow<List<String>>
}

class OfflineCocktailsRepository(
    private val cocktailDao: CocktailDao,
    private val cocktailApiService: CocktailApiService
): CocktailRepository{
    override suspend fun insertCocktail(cocktail: Cocktail) {
        cocktailDao.insertCocktailWithMeasurementsAndIngredientNames(
            cocktail.asDbCocktail(),
            cocktail.measurements!!.asDbMeasurements(),
            cocktail.ingredientNames!!.asDbIngredientNames()
        )
    }

    override suspend fun updateCocktail(cocktailId: Int, isFavorite: Boolean) {
        cocktailDao.updateIsFavorite(cocktailId,isFavorite)
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
    override  fun getCocktailMeasurements(id: Int):Flow<List<String>>{
        return flow { emit(cocktailDao.getItemM(id).measurements.asDomainMeasurements()) }
    }
    override  fun getCocktailIngredientNames(id: Int):Flow<List<String>>{
        return flow { emit(cocktailDao.getItemI(id).ingredientNames.asDomainIngredientNames()) }
    }

    override  fun getCocktailById(id: Int): Flow<Cocktail> {
        return  cocktailDao.getItem(id).map {
            it.asDomainCocktail()
        }
    }

    override suspend fun refresh() {
        //"bcdefghijklmnopqrstuvwxyz"
        val abc = "a"
        for(char in abc){
            cocktailApiService.getCocktailsAsFlow(char.toString()).collect {
                for (cocktail in it.drinks.asDomainObjects()){
                    insertCocktail(cocktail)
                }
            }
        }
    }

    override fun getCocktailsByCategory(filters: List<String>): Flow<List<Cocktail>> {
        if(filters.isEmpty()){
            return getAllCocktails()
        }

        val cocktails = emptyList<Cocktail>()
        for(filter in filters){
            cocktails.plus(cocktailDao.getAllItemsInCategory(filters.first()).map{
                it.asDomainCocktails()
            })
        }

        return flow{ emit(cocktails) }
    }
}
