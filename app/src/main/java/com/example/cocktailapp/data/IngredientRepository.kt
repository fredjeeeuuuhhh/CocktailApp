package com.example.cocktailapp.data


import com.example.cocktailapp.data.database.CocktailDao
import com.example.cocktailapp.data.database.IngredientDao
import com.example.cocktailapp.data.database.asDbIngredient
import com.example.cocktailapp.data.database.asDbIngredientNames
import com.example.cocktailapp.data.database.asDomainCocktails
import com.example.cocktailapp.data.database.asDomainIngredient
import com.example.cocktailapp.data.database.asDomainIngredientsNamesOnly
import com.example.cocktailapp.model.Cocktail
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.getIngredientAsFlow
import com.example.cocktailapp.network.getIngredientsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface IngredientRepository {
    suspend fun upsert(item: Ingredient)
    suspend fun updateIsOwned(ingredientId: Int, isOwned: Boolean)
    fun getAllItems(): Flow<List<Ingredient>>
    fun getItem(name: String): Flow<Ingredient>
    suspend fun refresh()
    suspend fun refreshIngredient(name:String)
    suspend fun getCocktailsForIngredient (name: String): List<Cocktail>
}

class OfflineIngredientsRepository(
    private val ingredientDao: IngredientDao,
    private val cocktailDao: CocktailDao,
    private val ingredientApiService: IngredientApiService
): IngredientRepository{
    override suspend fun upsert(item: Ingredient) {
       ingredientDao.upsert(item.asDbIngredient())
    }

    override suspend fun updateIsOwned(ingredientId: Int, isOwned: Boolean) {
        ingredientDao.updateIsOwned(ingredientId,isOwned)
    }

    //only the names
    override fun getAllItems(): Flow<List<Ingredient>> {
        return ingredientDao.getAllItems().map {
            it.asDomainIngredientsNamesOnly()
        }.onEach {
            if(it.isEmpty()){
                refresh()
            }
        }
    }

    override suspend fun getCocktailsForIngredient (name: String): List<Cocktail> {
         return cocktailDao.getItemsCocktails(name).asDomainCocktails()

    }
    override fun getItem(name: String): Flow<Ingredient> {
        return ingredientDao.getItem(name).map { ingredientEntity ->
            ingredientEntity.asDomainIngredient()
        }.onEach { ingredient ->
            if (ingredient.id == null) {
                refreshIngredient(name)
            }
        }
    }


    //inserting into ingredientnames
    override suspend fun refresh() {
       ingredientApiService.getIngredientsAsFlow().collect { apiIngredientNames ->
           cocktailDao.insertIngredientNames( apiIngredientNames.drinks.map { it.strIngredient1 }.asDbIngredientNames())
       }
    }

    //upsert full ingredient
    override suspend fun refreshIngredient(name:String) {
        ingredientApiService.getIngredientAsFlow(name).collect {
            upsert(ingredientApiService.getIngredientByName(name).ingredients.asDomainObject())
        }
    }
}



