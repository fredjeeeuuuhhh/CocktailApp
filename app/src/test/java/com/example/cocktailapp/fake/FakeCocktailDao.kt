package com.example.cocktailapp.fake

import com.example.cocktailapp.local.cocktails.CocktailDao
import com.example.cocktailapp.local.cocktails.CrossRef
import com.example.cocktailapp.local.cocktails.DbCocktail
import kotlinx.coroutines.flow.Flow

class FakeCocktailDao:CocktailDao {
    override suspend fun insertCocktail(cocktail: DbCocktail) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCrossRef(ref: CrossRef) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNrOfOwned(cocktailId: Int, nrOfOwned: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun updateIsFavorite(cocktailId: Int, isFavorite: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getByIngredientIdOnlyNoFlow(ingredientName: String): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getCocktailById(id: Int): DbCocktail {
        TODO("Not yet implemented")
    }

    override suspend fun getOwnedIngredientsForCocktail(cocktailId: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<DbCocktail>> {
        TODO("Not yet implemented")
    }

    override fun getById(cocktailId: Int): Flow<DbCocktail> {
        TODO("Not yet implemented")
    }

    override fun getByIngredient(ingredientName: String): Flow<List<DbCocktail>> {
        TODO("Not yet implemented")
    }
}