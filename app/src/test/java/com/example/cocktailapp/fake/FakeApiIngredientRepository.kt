package com.example.cocktailapp.fake

import com.example.cocktailapp.local.ingredients.IngredientRepository
import com.example.cocktailapp.model.Ingredient
import kotlinx.coroutines.flow.Flow

class FakeApiIngredientRepository() : IngredientRepository {
    override fun getIngredients(): Flow<List<Ingredient>> {
        TODO("Not yet implemented")
    }

    override fun getIngredientByName(name: String): Flow<Ingredient> {
        TODO("Not yet implemented")
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshIngredients() {
        TODO("Not yet implemented")
    }

    override suspend fun getIngredientByNameInWorker(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshIngredientsInWorker() {
        TODO("Not yet implemented")
    }
}
