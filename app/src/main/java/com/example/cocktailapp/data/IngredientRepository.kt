package com.example.cocktailapp.data

import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.asDomainObjectsWithNameOnly
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IngredientRepository {
    suspend fun getIngredients(): Flow<List<Ingredient>>
    suspend fun getIngredientByName(name: String): Flow<Ingredient>

}
class IngredientApiRepository(
    private val ingredientApiService: IngredientApiService
): IngredientRepository{
    override suspend fun getIngredients(): Flow<List<Ingredient>> = flow{
        emit( ingredientApiService.getIngredients().drinks.asDomainObjectsWithNameOnly() )
    }

    override suspend fun getIngredientByName(name: String): Flow<Ingredient> = flow{
        emit( ingredientApiService.getIngredientByName(name).ingredients.asDomainObject() )
    }
}