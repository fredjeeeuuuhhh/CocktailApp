package com.example.cocktailapp.local.ingredients

import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainImgredientNameOnly
import com.example.cocktailapp.network.asDomainIngredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException

interface IngredientRepository {
    fun getIngredients(): Flow<List<Ingredient>>
    suspend fun getIngredientByName(name: String): Flow<Ingredient>
    suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void>
    suspend fun refreshIngredients()
}

class OfflineIngredientRepository(
    private val ingredientDao: IngredientDao,
    private val cocktailDao: CocktailDao,
    private val ingredientApiService: IngredientApiService,
) : IngredientRepository {
    override fun getIngredients(): Flow<List<Ingredient>> {
        return ingredientDao.getAll().map { ingredients ->
            ingredients.map {
                it.toDomainIngredient()
            }
        }
    }

    override suspend fun getIngredientByName(name: String): Flow<Ingredient> {
        try {
            val ingredients = ingredientApiService.getIngredientByName(name).ingredients.map { it.asDomainIngredient() }
            ingredients.forEach {
                ingredientDao.updateIngredient(it.name, it.description ?: "", it.containsAlcohol ?: false, it.alcoholPercentage ?: "", it.type ?: "")
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return ingredientDao.getByName(name).map { it.toDomainIngredient() }
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void> = flow {
        ingredientDao.updateIsOwned(ingredientName, isOwned)
        cocktailDao.getByIngredientIdOnlyNoFlow(ingredientName).forEach { id ->
            cocktailDao.updateTransaction(id)
        }
    }

    override suspend fun refreshIngredients() {
        try {
            val ingredients = ingredientApiService.getIngredients().drinks.map { it.strIngredient1.asDomainImgredientNameOnly().asDbIngredient() }
            ingredients.forEach { ingredient ->
                ingredientDao.insertIngredientIfNotExisting(ingredient)
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }
}
