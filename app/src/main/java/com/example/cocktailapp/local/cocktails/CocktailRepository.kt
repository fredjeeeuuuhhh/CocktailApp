package com.example.cocktailapp.local.cocktails

import android.util.Log
import com.example.cocktailapp.local.ingredients.IngredientDao
import com.example.cocktailapp.local.ingredients.asDbIngredient
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.network.CocktailApiService
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainIngredient
import com.example.cocktailapp.network.asDomainObjects
import com.example.cocktailapp.network.asDomainObjectsFromSearch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException

interface CocktailRepository {
    fun getAll(): Flow<List<Cocktail>>
    suspend fun getCocktailById(id: Int): Flow<Cocktail>
    suspend fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>>

    suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void>
    suspend fun refreshCocktails()
}

class OfflineCocktailRepository(
    private val cocktailDao: CocktailDao,
    private val ingredientDao: IngredientDao,
    private val ingredientApiService: IngredientApiService,
    private val cocktailApiService: CocktailApiService,
) : CocktailRepository {
    // private val characters = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    private val characters = listOf("a")
    override fun getAll(): Flow<List<Cocktail>> {
        return cocktailDao.getAll().map { cocktails ->
            cocktails.map {
                it.toDomainCocktail()
            }
        }.onEach { cocktails ->
            if (cocktails.isEmpty()) {
                refreshCocktails()
            }
        }
    }

    override suspend fun getCocktailById(id: Int): Flow<Cocktail> {
        val ingredientsNames = cocktailApiService.getCocktailById(id).drinks?.asDomainObjects()
            ?.first()?.ingredientNames

        if (ingredientsNames != null) {
            ingredientsNames.forEach {
                val ingredient = ingredientApiService.getIngredientByName(it).ingredients.map { it.asDomainIngredient() }.first().asDbIngredient()
                Log.i("sg", ingredient.toString())
                ingredientDao.insertIngredient(ingredient)
                cocktailDao.insertCrossRef(CrossRef(id, it))
            }
        }

        return cocktailDao.getById(id).map {
            it.toDomainCocktail()
        }
    }

    override suspend fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>> {
        return cocktailDao.getByIngredient(ingredientName).map { cocktails ->
            cocktails.map {
                it.toDomainCocktail()
            }
        }.onEach { cocktails ->
            if (cocktails.isEmpty()) {
                searchByIngredientFromApi(ingredientName)
            }
        }
    }

    override suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void> = flow {
        cocktailDao.updateIsFavorite(cocktailId, isFavorite)
    }

    private suspend fun searchByIngredientFromApi(ingredientName: String) {
        try {
            val cocktails = cocktailApiService.searchByIngredient(ingredientName).drinks.asDomainObjectsFromSearch().map { it.asDbCocktail() }
            cocktailDao.insertCocktails(cocktails)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }
    override suspend fun refreshCocktails() {
        try {
            characters.forEach { character ->
                val cocktails = cocktailApiService.getCocktails(character).drinks?.asDomainObjects()
                    ?.map { it.asDbCocktail() }
                if (cocktails != null) {
                    cocktailDao.insertCocktails(cocktails)
                    cocktails.forEach {
                        it.ingredientNames.forEach { string ->
                            cocktailDao.insertCrossRef(CrossRef(it.cocktailId, string))
                        }
                    }
                }
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }
}
