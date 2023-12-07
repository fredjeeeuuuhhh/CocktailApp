package com.example.cocktailapp.data


import com.example.cocktailapp.data.database.IngredientDao
import com.example.cocktailapp.data.database.asDbIngredient
import com.example.cocktailapp.data.database.asDomainIngredient
import com.example.cocktailapp.data.database.asDomainIngredients
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.getIngredientsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface IngredientRepository {
    suspend fun upsert(item: Ingredient)
    fun updateIsOwned(ingredientId: Int, isOwned: Boolean): Flow<Ingredient>
    fun getAllItems(): Flow<List<Ingredient>>
    fun getItem(name: String): Flow<Ingredient>
    suspend fun refresh()
}

class OfflineIngredientsRepository(
    private val ingredientDao: IngredientDao,
    val ingredientApiService: IngredientApiService
): IngredientRepository{
    override suspend fun upsert(item: Ingredient) {
       ingredientDao.upsert(item.asDbIngredient())
    }

    override fun updateIsOwned(ingredientId: Int, isOwned: Boolean): Flow<Ingredient> {
        return ingredientDao.updateIsOwned(ingredientId,isOwned).map {
            it.asDomainIngredient()
        }
    }

    override fun getAllItems(): Flow<List<Ingredient>> {
        return ingredientDao.getAllItems().map {
            it.asDomainIngredients()
        }.onEach {
            if(it.isEmpty()){
                refresh()
            }
        }
    }

    override fun getItem(name: String): Flow<Ingredient> {
        return ingredientDao.getItem(name).map {
            it.asDomainIngredient()
        }
    }

    override suspend fun refresh() {
       ingredientApiService.getIngredientsAsFlow().collect {
           for(name in it.drinks){
               upsert(ingredientApiService.getIngredientByName(name.strIngredient1).ingredients.asDomainObject())
           }
       }
    }
}

