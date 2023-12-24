package com.example.cocktailapp.fake

import com.example.cocktailapp.local.ingredients.IngredientRepository
import com.example.cocktailapp.model.Ingredient
import com.example.cocktailapp.network.asDomainIngredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeApiIngredientRepository() : IngredientRepository {
    override fun getIngredients(): Flow<List<Ingredient>> = flow {
        emit(FakeDataSource.ingredients.map { it.asDomainIngredient() })
    }

    override fun getIngredientByName(name: String): Flow<Ingredient> = flow {
        emit(FakeDataSource.ingredients.find { it.strIngredient == name }!!.asDomainIngredient())
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean): Flow<Void> {
        return flow {}
    }

    override suspend fun refreshIngredients() {
    }

    override suspend fun getIngredientByNameInWorker(name: String) {
    }

    override suspend fun refreshIngredientsInWorker() {
    }
}
