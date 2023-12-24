package com.example.cocktailapp.fake

import com.example.cocktailapp.local.cocktails.CocktailRepository
import com.example.cocktailapp.model.Cocktail
import kotlinx.coroutines.flow.Flow

class FakeApiCocktailRepository() : CocktailRepository {
    override fun getAll(): Flow<List<Cocktail>> {
        TODO("Not yet implemented")
    }

    override fun getCocktailById(id: Int): Flow<Cocktail> {
        TODO("Not yet implemented")
    }

    override fun searchByIngredient(ingredientName: String): Flow<List<Cocktail>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean): Flow<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshCocktails() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshCocktailsInWorker() {
        TODO("Not yet implemented")
    }

    override suspend fun getCocktailByIdInWorker(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun searchByIngredientInWorker(ingredientName: String) {
        TODO("Not yet implemented")
    }
}
