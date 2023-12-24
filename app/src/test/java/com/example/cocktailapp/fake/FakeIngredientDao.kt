package com.example.cocktailapp.fake

import com.example.cocktailapp.local.ingredients.DbIngredient
import com.example.cocktailapp.local.ingredients.IngredientDao
import kotlinx.coroutines.flow.Flow

class FakeIngredientDao:IngredientDao {
    override suspend fun insertIngredient(ingredient: DbIngredient) {
        TODO("Not yet implemented")
    }

    override suspend fun getIngredientByName(name: String): DbIngredient {
        TODO("Not yet implemented")
    }

    override suspend fun updateIngredient(
        name: String,
        desc: String,
        alcohol: Boolean,
        percentage: String,
        type: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateIsOwned(ingredientName: String, isOwned: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<DbIngredient>> {
        TODO("Not yet implemented")
    }

    override fun getByName(ingredientName: String): Flow<DbIngredient> {
        TODO("Not yet implemented")
    }
}