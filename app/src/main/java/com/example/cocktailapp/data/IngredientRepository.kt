package com.example.cocktailapp.data

import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.IngredientApiService
import com.example.cocktailapp.network.asDomainObject
import com.example.cocktailapp.network.asDomainObjectsWithNameOnly

interface IngredientRepository {
    suspend fun getIngredients(): List<Ingredient>
    suspend fun getIngredientByName(name: String): Ingredient

}
class IngredientApiRepository(
    val ingredientApiService: IngredientApiService
): IngredientRepository{
    override suspend fun getIngredients(): List<Ingredient> =
        ingredientApiService.getIngredients().drinks.asDomainObjectsWithNameOnly()
    override suspend fun getIngredientByName(name: String): Ingredient =
        ingredientApiService.getIngredientByName(name).ingredients.asDomainObject()
}